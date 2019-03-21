package br.com.jonathan.application.usecase.payment;

import br.com.jonathan.application.dto.RefundPaymentDTO;
import br.com.jonathan.application.dto.RequestRefundDTO;
import br.com.jonathan.application.pattern.specification.validation.ErrorsSpecificationPatternValidation;
import br.com.jonathan.application.pattern.specification.validation.payment.PaymentExistsSpecification;
import br.com.jonathan.application.pattern.specification.validation.payment.PaymentStatusRefundSpecification;
import br.com.jonathan.application.resource.ResourceDTOSupport;
import br.com.jonathan.application.service.RefundEventService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class RefundPaymentUseCase {
    private static final Logger logger = LogManager.getLogger(RefundPaymentUseCase.class);

    private final ErrorsSpecificationPatternValidation<String> pattern;

    @Inject
    private RefundEventService service;

    @Inject
    public RefundPaymentUseCase(PaymentExistsSpecification paymentExistsSpecification, PaymentStatusRefundSpecification paymentStatusRefundSpecification) {
        super();
        this.pattern = ErrorsSpecificationPatternValidation.of(paymentExistsSpecification, paymentStatusRefundSpecification);
    }

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceDTOSupport<RefundPaymentDTO> execute(String id, RequestRefundDTO refund, Supplier<Link> supplier) {
        logger.info("Refund payment by id={}!", id);
        pattern.validade(id);
        RefundPaymentDTO dto = RefundPaymentDTO.of(id, refund);
        service.emit(dto);
        return ResourceDTOSupport.of(
                dto,
                supplier
        );
    }

}