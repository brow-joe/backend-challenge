package br.com.jonathan.application.usecase.store;

import br.com.jonathan.application.resource.ResourceListSupport;
import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.repository.StoreRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.function.Supplier;

@Component
public class FindAllStoreUseCase {
    private static final Logger logger = LogManager.getLogger(FindAllStoreUseCase.class);

    @Inject
    private StoreRepository repository;

    @HystrixCommand
    public ResourceListSupport<StoreEntity> execute(Supplier<Link> supplier) {
        logger.info("Find all store!");
        return ResourceListSupport.of(
                repository.findAll(),
                supplier
        );
    }

}
