package br.com.jonathan.application.usecase.payment;

import br.com.jonathan.application.dto.PaymentDTO;
import br.com.jonathan.application.pattern.specification.validation.ErrorsSpecificationPatternValidation;
import br.com.jonathan.application.pattern.specification.validation.payment.OrderContainsPaymentSpecification;
import br.com.jonathan.application.pattern.specification.validation.payment.PaymentContainsOrderSpecification;
import br.com.jonathan.application.pattern.specification.validation.payment.PaymentIsValidFieldsSpecification;
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
public class CreatePaymentUseCase {
    private static final Logger logger = LogManager.getLogger(CreatePaymentUseCase.class);

    private final ErrorsSpecificationPatternValidation<PaymentEntity> pattern;

    @Inject
    private PaymentRepository repository;

    @Inject
    public CreatePaymentUseCase(
            PaymentContainsOrderSpecification paymentContainsOrderSpecification,
            OrderContainsPaymentSpecification orderContainsPaymentSpecification,
            PaymentIsValidFieldsSpecification paymentIsValidFieldsSpecification
    ) {
        super();
        this.pattern = ErrorsSpecificationPatternValidation.of(
                paymentContainsOrderSpecification,
                orderContainsPaymentSpecification,
                paymentIsValidFieldsSpecification
        );
    }

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceDataSupport<PaymentEntity> execute(PaymentDTO dto, Supplier<Link> supplier) {
        logger.info("Create payment={}!", dto);
        PaymentEntity payment = dto.toEntity();
        pattern.validade(payment);
        return ResourceDataSupport.of(
                repository.save(payment),
                supplier
        );
    }

}
