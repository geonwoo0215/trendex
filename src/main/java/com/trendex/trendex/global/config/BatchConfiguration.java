package com.trendex.trendex.global.config;


import com.trendex.trendex.domain.orderbooktradeaggregate.model.OrderbookTradeAggregate;
import com.trendex.trendex.domain.trade.upbittrade.dto.TradeAndOrderBookUnitDto;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    @Bean
    public Job aggregateJob(JobRepository jobRepository, Step aggregateStep) {
        return new JobBuilder("aggregateJob", jobRepository)
                .start(aggregateStep)
                .build();
    }

    @Bean
    public Step aggregateStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                              ItemReader<TradeAndOrderBookUnitDto> itemReader,
                              ItemProcessor<TradeAndOrderBookUnitDto, OrderbookTradeAggregate> itemProcessor,
                              ItemWriter<OrderbookTradeAggregate> itemWriter) {
        return new StepBuilder("aggregateStep", jobRepository)
                .<TradeAndOrderBookUnitDto, OrderbookTradeAggregate>chunk(100, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public ItemReader<TradeAndOrderBookUnitDto> itemReader(EntityManagerFactory entityManagerFactory) {

        LocalDateTime startTimeLocal = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0).minusHours(1);
        LocalDateTime endTimeLocal = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        Long startTime = Timestamp.valueOf(startTimeLocal).getTime();
        Long endTime = Timestamp.valueOf(endTimeLocal).getTime();

        return new JpaPagingItemReaderBuilder<TradeAndOrderBookUnitDto>()
                .name("tradeAndOrderBookItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT " +
                        "ut.market, ut.timestamp, ut.tradePrice, ut.tradeVolume, " +
                        "obu.askPrice, obu.bidPrice, obu.askSize, obu.bidSize " +
                        "FROM UpbitTrade ut " +
                        "JOIN UpbitOrderBook ob ON ut.market = ob.market " +
                        "JOIN UpbitOrderBookUnit obu ON ob.id = obu.upbitOrderBook.id " +
                        "WHERE ut.timestamp BETWEEN :startTime AND :endTime")
                .parameterValues(Map.of("startTime", startTime, "endTime", endTime))
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<TradeAndOrderBookUnitDto, OrderbookTradeAggregate> itemProcessor() {
        return item -> {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(item.getTimestamp()), ZoneId.systemDefault()
            ).withMinute(0).withSecond(0).withNano(0);

            return new OrderbookTradeAggregate(
                    item.getMarket(),
                    item.getTradePrice(),
                    item.getTradeVolume(),
                    item.getAskPrice(),
                    item.getBidPrice(),
                    item.getAskSize(),
                    item.getBidSize(),
                    localDateTime
            );
        };
    }

    @Bean
    public ItemWriter<OrderbookTradeAggregate> itemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<OrderbookTradeAggregate>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}