package br.com.jonathan.application.usecase.payment;

import br.com.jonathan.application.dto.PaymentDTO;
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
public class UpdatePaymentUseCase {
    private static final Logger logger = LogManager.getLogger(UpdatePaymentUseCase.class);

    @Inject
    private PaymentRepository repository;

    @HystrixCommand
    public ResourceDataSupport<PaymentEntity> execute(String id, PaymentDTO dto, Supplier<Link> supplier) {
        logger.info("Update payment={}, id={}!", dto, id);
        return ResourceDataSupport.of(
                repository.update(
                        dto.toEntity(id)
                ),
                supplier
        );
    }

}