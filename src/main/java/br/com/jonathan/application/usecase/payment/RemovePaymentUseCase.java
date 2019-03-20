package br.com.jonathan.application.usecase.payment;

import br.com.jonathan.domain.repository.PaymentRepository;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.function.Supplier;

@Component
public class RemovePaymentUseCase {
    private static final Logger logger = LogManager.getLogger(RemovePaymentUseCase.class);

    @Inject
    private PaymentRepository repository;

    @HystrixCommand
    public ResourceSupport execute(String id, Supplier<Link> supplier) throws EntityNotFoundException {
        logger.info("Remove payment by id={}!", id);
        ResourceSupport resource = new ResourceSupport();
        repository.delete(id);
        resource.add(supplier.get());
        return resource;
    }

}