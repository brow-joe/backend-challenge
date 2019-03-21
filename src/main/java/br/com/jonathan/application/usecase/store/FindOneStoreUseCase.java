package br.com.jonathan.application.usecase.store;

import br.com.jonathan.application.resource.ResourceDataSupport;
import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.repository.StoreRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class FindOneStoreUseCase {
    private static final Logger logger = LogManager.getLogger(FindOneStoreUseCase.class);

    @Inject
    private StoreRepository repository;

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceDataSupport<StoreEntity> execute(String id, Supplier<Link> supplier) {
        logger.info("Find store by id={}!", id);
        return ResourceDataSupport.of(
                repository.findOne(id),
                supplier
        );
    }

}
