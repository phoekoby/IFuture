package com.example.server.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Configuration
@Aspect
@Slf4j
public class AspectBalancerConfiguration {
    private static Long counterGet = 0L;
    private static Long counterChange = 0L;

    private static LocalDateTime started = null;

    private void init() {
        if (started == null) {
            started = LocalDateTime.now();
        }
    }

    private void loggingBoth() {
        Long time = (ChronoUnit.SECONDS.between(started, LocalDateTime.now())) + 1;
        log.info("Executed Request: {}, in {} seconds", counterChange + counterGet, time);
        log.info("Executed Amount of Request/Seconds: {}", (counterChange + counterGet) / ((ChronoUnit.SECONDS.between(started, LocalDateTime.now())) + 1));
    }

    @Before("(execution(public * com.example.server.service.impl.BalanceServiceImpl.getBalance(..)))")
    public void loggingGet() {
        init();
        counterGet++;
        Long time = (ChronoUnit.SECONDS.between(started, LocalDateTime.now())) + 1;
        log.info("Executed Get Request: {}, in {} seconds", counterGet, time);
        log.info("Executed Amount of Get Request/Seconds: {}",  counterGet/((ChronoUnit.SECONDS.between(started, LocalDateTime.now())) + 1));
        loggingBoth();
    }

    @Before("(execution(public * com.example.server.service.impl.BalanceServiceImpl.changeBalance(..)))")
    public void loggingChange() {
        init();
        counterChange++;
        Long time = (ChronoUnit.SECONDS.between(started, LocalDateTime.now())) + 1;
        log.info("Executed Change Request: {}, in {} seconds", counterChange, time );
        log.info("Executed Amount of Change Request/Seconds: {}",  counterChange/((ChronoUnit.SECONDS.between(started, LocalDateTime.now())) + 1));
        loggingBoth();
    }
}
