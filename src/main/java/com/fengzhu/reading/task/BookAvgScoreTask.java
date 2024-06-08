package com.fengzhu.reading.task;

import com.fengzhu.reading.dataObject.Book;
import com.fengzhu.reading.dataObject.BookAvgRating;
import com.fengzhu.reading.dataObject.BookRating;
import com.fengzhu.reading.repository.BookAvgRatingRepository;
import com.fengzhu.reading.repository.BookRatingRepository;
import com.fengzhu.reading.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BookAvgScoreTask {

    @Autowired
    private BookRatingRepository bookRatingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookAvgRatingRepository bookAvgRatingRepository;

    private Executor executor = new ThreadPoolExecutor(8, 20,
            10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

    @Scheduled(cron = "0 0 * * * *")
    public void calculateBookScore() {
        List<Long> bookIdList = bookRepository.findAll().stream().map(Book::getId).toList();
        Instant now = Instant.now();
        long endTime = now.toEpochMilli();
        long beginTime = now.minus(Duration.ofHours(1)).toEpochMilli();
        for (Long bookId : bookIdList) {
            executor.execute(() -> {
                try {
                    List<BookRating> bookRatingList = bookRatingRepository.findByBookIdAndRateDateBetween(bookId, beginTime, endTime);
                    if (CollectionUtils.isEmpty(bookRatingList)) {
                        return;
                    }

                    float averageScore = (float) bookRatingList.stream().map(BookRating::getScore)
                            .mapToDouble(Double::valueOf).average().orElse(0.0);

                    BookAvgRating lastAvgRating = bookAvgRatingRepository.findByBookIdAndEnable(bookId, true).orElse(null);
                    lastAvgRating.setEnable(false);
                    bookAvgRatingRepository.save(lastAvgRating);
                    log.info("[calculateBookScore:] {}, last avg score update successful", bookId);
                    BookAvgRating bookAvgRating = BookAvgRating.builder()
                            .bookId(bookId)
                            .avgRatingScore(averageScore)
                            .enable(true).build();

                    bookAvgRatingRepository.save(bookAvgRating);
                    log.info("[calculateBookScore:] {}, current avg score update successful", bookId);
                } catch (Throwable throwable) {
                    log.error("calculateBookScore execute fail, {}", bookId, throwable);
                }

            });
        }
    }
}
