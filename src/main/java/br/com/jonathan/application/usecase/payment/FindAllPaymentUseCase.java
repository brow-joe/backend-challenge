package br.com.jonathan.application.usecase.payment;

import br.com.jonathan.application.resource.ResourceListSupport;
import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.repository.PaymentRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class FindAllPaymentUseCase {
    private static final Logger logger = LogManager.getLogger(FindAllPaymentUseCase.class);

    @Inject
    private PaymentRepository repository;

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceListSupport<PaymentEntity> execute(Supplier<Link> supplier) {
        logger.info("Find all payment!");
        return ResourceListSupport.of(
                repository.findAll(),
                supplier
        );
    }

}
