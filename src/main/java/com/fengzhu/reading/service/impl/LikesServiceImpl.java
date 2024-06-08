package com.fengzhu.reading.service.impl;

import com.fengzhu.reading.dataObject.LikesStatistic;
import com.fengzhu.reading.dataObject.LikesUserRecord;
import com.fengzhu.reading.dto.LikesUserRecordDTO;
import com.fengzhu.reading.repository.LikesStatisticRepository;
import com.fengzhu.reading.repository.LikesUserRecordRepository;
import com.fengzhu.reading.service.LikesService;
import com.fengzhu.reading.service.RabbitmqService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.fengzhu.reading.Constants.LIKE_STATISTIC_KEY;
import static com.fengzhu.reading.Constants.LIKE_USER_KEY;

@Service
@Slf4j
public class LikesServiceImpl implements LikesService {

    @Autowired
    private RabbitmqService rabbitmqService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LikesUserRecordRepository likesUserRecordRepository;

    @Autowired
    private LikesStatisticRepository likesStatisticRepository;

    @Override
    public boolean addNewLikesRecord(LikesUserRecordDTO likesUserRecordDTO) {
        rabbitmqService.publishMessage(likesUserRecordDTO);
        return true;
    }

    @Override
    public List<Long> getMyLikes(long userId, Long businessId) {
        List<Long> likesItemIdList = Lists.newArrayList();
        Set<String> allMyLikes = redisTemplate.opsForSet().members(LIKE_USER_KEY + userId);
        if (allMyLikes != null && !allMyLikes.isEmpty()) {
            for (String like : allMyLikes) {
                likesItemIdList.add(Long.valueOf(like.split(":")[1]));
            }
            log.info("load my likes from cache: businessId:{}, userId:{}", businessId, userId);
            return likesItemIdList;
        }

        List<LikesUserRecord> likesUserRecordDTOList = likesUserRecordRepository
                .findByUserIdAndBusinessIdAndLikes(userId, businessId, true);

        log.info("load my likes from db: businessId:{}, userId:{}", businessId, userId);
        return likesUserRecordDTOList == null ? Lists.newArrayList() :
                likesUserRecordDTOList.stream().map(LikesUserRecord::getItemId).toList();
    }

    @Override
    public Long getItemLikesCount(Long businessId, Long itemId) {
        String statisticKey = LIKE_STATISTIC_KEY + businessId + ":" + itemId;
        Long count = (Long) redisTemplate.opsForValue().get(statisticKey);
        if (count != null) {
            log.info("load item like count from cache: businessId:{}, itemId:{}", businessId, itemId);
            return count;
        }

        LikesStatistic likesStatistic = likesStatisticRepository.findByBusinessIdAndItemId(businessId, itemId).orElse(null);
        log.info("load item like count from db: businessId:{}, itemId:{}", businessId, itemId);
        return likesStatistic == null ? 0L : likesStatistic.getLikeCount();
    }
}
