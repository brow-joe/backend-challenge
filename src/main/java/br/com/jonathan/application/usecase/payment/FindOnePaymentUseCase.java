package br.com.jonathan.application.usecase.payment;

import br.com.jonathan.application.resource.ResourceDataSupport;
import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.repository.PaymentRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.function.Supplier;

@Component
public class FindOnePaymentUseCase {
    private static final Logger logger = LogManager.getLogger(FindOnePaymentUseCase.class);

    @Inject
    private PaymentRepository repository;

    @HystrixCommand
    public ResourceDataSupport<PaymentEntity> execute(String id, Supplier<Link> supplier) {
        logger.info("Find payment by id={}!", id);
        return ResourceDataSupport.of(
                repository.findOne(id),
                supplier
        );
    }

}
