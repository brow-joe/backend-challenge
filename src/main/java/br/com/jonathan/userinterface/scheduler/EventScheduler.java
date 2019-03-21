package br.com.jonathan.userinterface.scheduler;

import br.com.jonathan.application.handler.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class EventScheduler {
    private static final Logger logger = LogManager.getLogger(EventScheduler.class);

    @Inject
    private EventHandler handler;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void listener() {
        logger.info("Event task");
        handler.execute();
    }

}
