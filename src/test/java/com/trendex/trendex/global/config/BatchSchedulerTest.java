package com.trendex.trendex.global.config;

import com.trendex.trendex.global.client.webclient.service.TelegramService;
import com.trendex.trendex.global.client.webclient.service.TelegramWebClientService;
import com.trendex.trendex.global.common.util.RedisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBatchTest
@SpringBootTest
class BatchSchedulerTest {

    @MockBean
    TelegramWebClientService telegramWebClientService;

    @MockBean
    TelegramService telegramService;

    @MockBean
    RedisUtil redisUtil;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void testJobExecution() throws Exception {
        JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

}