package br.com.jonathan.userinterface.controller;

import br.com.jonathan.application.dto.StoreDTO;
import br.com.jonathan.application.resource.ResourceDataSupport;
import br.com.jonathan.application.resource.ResourceListSupport;
import br.com.jonathan.application.usecase.store.*;
import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.inject.Inject;

@CrossOrigin
@RestController
@RequestMapping("/${spring.application.version}/store")
public class StoreRestController extends AbstractedRestController<StoreRestController> {

    @Inject
    private FindAllStoreUseCase findAllStoreUseCase;
    @Inject
    private FindOneStoreUseCase findOneStoreUseCase;
    @Inject
    private CreateStoreUseCase createStoreUseCase;
    @Inject
    private UpdateStoreUseCase updateStoreUseCase;
    @Inject
    private RemoveStoreUseCase removeStoreUseCase;

    @Inject
    public StoreRestController(@Value("${spring.application.version}") String version) {
        super(StoreRestController.class, version);
    }

    @GetMapping
    public ResponseEntity<ResourceListSupport<StoreEntity>> findAll() {
        ResourceListSupport<StoreEntity> resource = findAllStoreUseCase
                .execute(() -> withSelf(linked().findAll()));
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDataSupport<StoreEntity>> findOne(@PathVariable("id") String id) {
        ResourceDataSupport<StoreEntity> resource = findOneStoreUseCase
                .execute(id, () -> withSelf(linked().findOne(id)));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<ResourceDataSupport<StoreEntity>> create(@RequestBody StoreDTO store) {
        ResourceDataSupport<StoreEntity> resource = createStoreUseCase
                .execute(store, () -> withSelf(linked().create(store)));
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDataSupport<StoreEntity>> update(@PathVariable("id") String id, @RequestBody StoreDTO store) {
        ResourceDataSupport<StoreEntity> resource = updateStoreUseCase
                .execute(id, store, () -> withSelf(linked().update(id, store)));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceSupport> remove(@PathVariable("id") String id) {
        try {
            ResourceSupport resource = removeStoreUseCase
                    .execute(id, () -> withSelf(linked().remove(id)));
            return ResponseEntity.ok(resource);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Entity not found!",
                    e
            );
        }
    }

}
