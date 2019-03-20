package br.com.jonathan.application.usecase.order;

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
public class FindOneOrderUseCase {
    private static final Logger logger = LogManager.getLogger(FindOneOrderUseCase.class);

    @Inject
    private OrderRepository repository;

    @HystrixCommand
    public ResourceDataSupport<OrderEntity> execute(String id, Supplier<Link> supplier) {
        logger.info("Find order by id={}!", id);
        return ResourceDataSupport.of(
                repository.findOne(id),
                supplier
        );
    }

}
