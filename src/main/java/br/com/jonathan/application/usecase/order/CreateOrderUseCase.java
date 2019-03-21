package br.com.jonathan.application.usecase.order;

import br.com.jonathan.application.dto.OrderDTO;
import br.com.jonathan.application.pattern.specification.validation.ErrorsSpecificationPatternValidation;
import br.com.jonathan.application.pattern.specification.validation.order.OrderContainsStoreSpecification;
import br.com.jonathan.application.pattern.specification.validation.order.OrderIsValidFieldsSpecification;
import br.com.jonathan.application.resource.ResourceDataSupport;
import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class CreateOrderUseCase {
    private static final Logger logger = LogManager.getLogger(CreateOrderUseCase.class);

    private final ErrorsSpecificationPatternValidation<OrderEntity> pattern;

    @Inject
    private OrderRepository repository;

    @Inject
    public CreateOrderUseCase(OrderContainsStoreSpecification orderContainsStoreSpecification, OrderIsValidFieldsSpecification orderIsValidFieldsSpecification) {
        super();
        this.pattern = ErrorsSpecificationPatternValidation.of(orderContainsStoreSpecification, orderIsValidFieldsSpecification);
    }

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceDataSupport<OrderEntity> execute(OrderDTO dto, Supplier<Link> supplier) {
        logger.info("Create order={}!", dto);
        OrderEntity order = dto.toEntity();
        pattern.validade(order);
        return ResourceDataSupport.of(
                repository.save(order),
                supplier
        );
    }

}
