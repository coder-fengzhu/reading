package com.fengzhu.reading.consumer;


import com.fengzhu.reading.converter.LikesUserRecordConverter;
import com.fengzhu.reading.dataObject.LikesStatistic;
import com.fengzhu.reading.dataObject.LikesUserRecord;
import com.fengzhu.reading.dto.LikesUserRecordDTO;
import com.fengzhu.reading.repository.LikesStatisticRepository;
import com.fengzhu.reading.repository.LikesUserRecordRepository;
import com.fengzhu.reading.validator.LikesValidator;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.fengzhu.reading.Constants.LIKE_STATISTIC_KEY;
import static com.fengzhu.reading.Constants.LIKE_USER_KEY;

@Component
@Slf4j
@Data
public class LikesMessageConsumer {

    @Value("${rabbitmq.reading.queue}")
    private String queueName;

    @Value("${rabbitmq.reading.exchange}")
    private String exchange;

    @Autowired
    private LikesUserRecordRepository likesUserRecordRepository;

    @Autowired
    private LikesStatisticRepository likesStatisticRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LikesValidator likesValidator;

    private Gson gson = new Gson();

    @RabbitListener(queues = "${rabbitmq.reading.queue}")
    public void handleMessage(LikesUserRecordDTO likesUserRecordDTO) {
        log.info("Received message from queue {}: message:{}", queueName, gson.toJson(likesUserRecordDTO));
        likesValidator.validateAddNewBook(likesUserRecordDTO);
        likesUserRecordRepository.findUserLikeRecord(likesUserRecordDTO.getUserId(), likesUserRecordDTO.getBusinessId(),
                likesUserRecordDTO.getItemId())
                .ifPresentOrElse(likesUserRecord -> doUnLikeAction(likesUserRecord), () -> doLikeAction(likesUserRecordDTO));
    }

    private void doLikeAction(LikesUserRecordDTO likesUserRecordDTO) {
        LikesUserRecord likesUserRecord = LikesUserRecordConverter.convertToLikesUserRecord(likesUserRecordDTO);
        likesUserRecordRepository.save(likesUserRecord);

        String likesStatisticKey = LIKE_STATISTIC_KEY + likesUserRecordDTO.getBusinessId() + ":" + likesUserRecordDTO.getItemId();

        likesStatisticRepository.findByBusinessIdAndItemId(likesUserRecordDTO.getBusinessId(),
                likesUserRecordDTO.getItemId())
                .ifPresentOrElse(likesStatistic -> {
                    long newLikeCount = likesStatistic.getLikeCount() + 1;
                    likesStatistic.setLikeCount(newLikeCount);
                    likesStatisticRepository.save(likesStatistic);
                    redisTemplate.opsForValue().set(likesStatisticKey, newLikeCount);
                }, () -> {
                    LikesStatistic likesStatistic = LikesStatistic.builder()
                            .businessId(likesUserRecordDTO.getBusinessId())
                            .itemId(likesUserRecordDTO.getItemId())
                            .likeCount(1).build();

                    likesStatisticRepository.save(likesStatistic);
                    redisTemplate.opsForValue().set(likesStatisticKey, 1L);
                });

        redisTemplate.opsForSet().add(LIKE_USER_KEY + likesUserRecordDTO.getUserId(), likesUserRecordDTO.getBusinessId() + ":" +
                likesUserRecordDTO.getItemId());

    }

    private void doUnLikeAction(LikesUserRecord likesUserRecord) {
        likesUserRecord.setLikes(false);
        likesUserRecordRepository.save(likesUserRecord);

        String likesStatisticKey = LIKE_STATISTIC_KEY + likesUserRecord.getBusinessId() + ":" + likesUserRecord.getItemId();
        likesStatisticRepository.findByBusinessIdAndItemId(likesUserRecord.getBusinessId(),
                likesUserRecord.getItemId())
                .ifPresentOrElse(likesStatistic -> {
                    long newLikeCount = likesStatistic.getLikeCount() - 1;
                    likesStatistic.setLikeCount(newLikeCount);
                    likesStatisticRepository.save(likesStatistic);

                    redisTemplate.opsForValue().set(likesStatisticKey, newLikeCount);
                }, () -> {
                    log.info("there is no likes statistic for businessId:{}, itemId:{}",
                            likesUserRecord.getBusinessId(), likesUserRecord.getItemId());
                });

        redisTemplate.opsForSet().remove(LIKE_USER_KEY + likesUserRecord.getUserId(), likesUserRecord.getBusinessId() + ":" +
                likesUserRecord.getItemId());

    }

}
