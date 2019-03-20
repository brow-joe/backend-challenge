package br.com.jonathan.application.usecase.payment;

import br.com.jonathan.application.dto.PaymentDTO;
import br.com.jonathan.application.pattern.specification.validation.ErrorsSpecificationPatternValidation;
import br.com.jonathan.application.pattern.specification.validation.payment.PaymentContainsOrderSpecification;
import br.com.jonathan.application.resource.ResourceDataSupport;
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
public class UpdatePaymentUseCase {
    private static final Logger logger = LogManager.getLogger(UpdatePaymentUseCase.class);

    private final ErrorsSpecificationPatternValidation<PaymentEntity> pattern;

    @Inject
    private PaymentRepository repository;

    @Inject
    public UpdatePaymentUseCase(PaymentContainsOrderSpecification paymentContainsOrderSpecification) {
        super();
        this.pattern = ErrorsSpecificationPatternValidation.of(paymentContainsOrderSpecification);
    }

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceDataSupport<PaymentEntity> execute(String id, PaymentDTO dto, Supplier<Link> supplier) {
        logger.info("Update payment={}, id={}!", dto, id);
        PaymentEntity payment = dto.toEntity(id);
        pattern.validade(payment);
        return ResourceDataSupport.of(
                repository.update(payment),
                supplier
        );
    }

}