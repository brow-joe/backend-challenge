package br.com.jonathan.application.usecase.order;

import br.com.jonathan.application.dto.OrderDTO;
import br.com.jonathan.application.resource.ResourceDataSupport;
import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.function.Supplier;

@Component
public class UpdateOrderUseCase {
    private static final Logger logger = LogManager.getLogger(UpdateOrderUseCase.class);

    @Inject
    private OrderRepository repository;

    @HystrixCommand
    public ResourceDataSupport<OrderEntity> execute(String id, OrderDTO dto, Supplier<Link> supplier) {
        logger.info("Update order={}, id={}!", dto, id);
        return ResourceDataSupport.of(
                repository.update(
                        dto.toEntity(id)
                ),
                supplier
        );
    }

}