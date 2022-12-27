package com.project.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.project.springbatch.job
 * fileName       : SimpleJobConfiguration
 * author         : jaeminlim
 * date           : 2022/12/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/12/23        jaeminlim       최초 생성
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory  stepBuilderFactory;

    @Bean
    public Job simpleJob(){
        return jobBuilderFactory.get("simpleJob")
                .start(step1(null))
                .next(step2(null))
                .build();
    }

    @Bean
    @JobScope
    public Step step1(@Value("#{jobParameters[requestDate]}")String requestDate) {
        return stepBuilderFactory.get("simpleStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step1 시작");
                    log.info("requestDate->{}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
        
    }

    @Bean
    @JobScope
    public Step step2(@Value("#{jobParameters[requestDate]}")String requestDate) {
        return stepBuilderFactory.get("simpleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step2 시작");
                    log.info("requestDate->{}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();

    }


}
