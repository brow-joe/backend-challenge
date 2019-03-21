package br.com.jonathan.userinterface.scheduler;

import br.com.jonathan.application.handler.PaymentAutoCompletedHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PaymentAutoCompletedScheduler {
    private static final Logger logger = LogManager.getLogger(PaymentAutoCompletedScheduler.class);

    @Inject
    private PaymentAutoCompletedHandler handler;

    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void listener() {
        logger.info("Payment auto completed task");
        handler.execute();
    }

}
