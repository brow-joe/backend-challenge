package br.com.jonathan.userinterface.controller;

import br.com.jonathan.application.dto.OrderDTO;
import br.com.jonathan.application.resource.ResourceDataSupport;
import br.com.jonathan.application.resource.ResourceListSupport;
import br.com.jonathan.application.usecase.order.*;
import br.com.jonathan.domain.entity.OrderEntity;
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
@RequestMapping("/${spring.application.version}/order")
public class OrderRestController extends AbstractedRestController<OrderRestController> {

    @Inject
    private FindAllOrderUseCase findAllOrderUseCase;
    @Inject
    private FindOneOrderUseCase findOneOrderUseCase;
    @Inject
    private CreateOrderUseCase createOrderUseCase;
    @Inject
    private UpdateOrderUseCase updateOrderUseCase;
    @Inject
    private RemoveOrderUseCase removeOrderUseCase;

    @Inject
    public OrderRestController(@Value("${spring.application.version}") String version) {
        super(OrderRestController.class, version);
    }

    @GetMapping
    public ResponseEntity<ResourceListSupport<OrderEntity>> findAll() {
        ResourceListSupport<OrderEntity> resource = findAllOrderUseCase
                .execute(() -> withSelf(linked().findAll()));
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDataSupport<OrderEntity>> findOne(@PathVariable("id") String id) {
        ResourceDataSupport<OrderEntity> resource = findOneOrderUseCase
                .execute(id, () -> withSelf(linked().findOne(id)));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<ResourceDataSupport<OrderEntity>> create(@RequestBody OrderDTO order) {
        ResourceDataSupport<OrderEntity> resource = createOrderUseCase
                .execute(order, () -> withSelf(linked().create(order)));
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDataSupport<OrderEntity>> update(@PathVariable("id") String id, @RequestBody OrderDTO order) {
        ResourceDataSupport<OrderEntity> resource = updateOrderUseCase
                .execute(id, order, () -> withSelf(linked().update(id, order)));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceSupport> remove(@PathVariable("id") String id) {
        try {
            ResourceSupport resource = removeOrderUseCase
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