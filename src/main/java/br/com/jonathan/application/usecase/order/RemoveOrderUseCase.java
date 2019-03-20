package br.com.jonathan.application.usecase.order;

import br.com.jonathan.domain.repository.OrderRepository;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class RemoveOrderUseCase {
    private static final Logger logger = LogManager.getLogger(RemoveOrderUseCase.class);

    @Inject
    private OrderRepository repository;

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceSupport execute(String id, Supplier<Link> supplier) throws EntityNotFoundException {
        logger.info("Remove order by id={}!", id);
        ResourceSupport resource = new ResourceSupport();
        repository.delete(id);
        resource.add(supplier.get());
        return resource;
    }

}