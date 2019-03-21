package br.com.jonathan.application.usecase.store;

import br.com.jonathan.application.dto.StoreDTO;
import br.com.jonathan.application.pattern.specification.validation.ErrorsSpecificationPatternValidation;
import br.com.jonathan.application.pattern.specification.validation.store.StoreIsValidFieldsSpecification;
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
public class CreateStoreUseCase {
    private static final Logger logger = LogManager.getLogger(CreateStoreUseCase.class);

    private final ErrorsSpecificationPatternValidation<StoreEntity> pattern;

    @Inject
    private StoreRepository repository;

    @Inject
    public CreateStoreUseCase(StoreIsValidFieldsSpecification storeIsValidFieldsSpecification) {
        super();
        pattern = ErrorsSpecificationPatternValidation.of(storeIsValidFieldsSpecification);
    }

    @HystrixCommand
    @Transactional(rollbackOn = Throwable.class)
    public ResourceDataSupport<StoreEntity> execute(StoreDTO dto, Supplier<Link> supplier) {
        logger.info("Create store={}!", dto);
        StoreEntity store = dto.toEntity();
        pattern.validade(store);
        return ResourceDataSupport.of(
                repository.save(store),
                supplier
        );
    }

}
