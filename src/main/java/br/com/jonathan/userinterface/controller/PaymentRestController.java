package br.com.jonathan.userinterface.controller;

import br.com.jonathan.application.dto.PaymentDTO;
import br.com.jonathan.application.dto.RefundPaymentDTO;
import br.com.jonathan.application.dto.RequestRefundDTO;
import br.com.jonathan.application.resource.ResourceDTOSupport;
import br.com.jonathan.application.resource.ResourceDataSupport;
import br.com.jonathan.application.resource.ResourceListSupport;
import br.com.jonathan.application.usecase.payment.*;
import br.com.jonathan.domain.entity.PaymentEntity;
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
@RequestMapping("/${spring.application.version}/payment")
public class PaymentRestController extends AbstractedRestController<PaymentRestController> {

    @Inject
    private FindAllPaymentUseCase findAllPaymentUseCase;
    @Inject
    private FindOnePaymentUseCase findOnePaymentUseCase;
    @Inject
    private CreatePaymentUseCase createPaymentUseCase;
    @Inject
    private UpdatePaymentUseCase updatePaymentUseCase;
    @Inject
    private RemovePaymentUseCase removePaymentUseCase;
    @Inject
    private RefundPaymentUseCase refundPaymentUseCase;

    @Inject
    public PaymentRestController(@Value("${spring.application.version}") String version) {
        super(PaymentRestController.class, version);
    }

    @GetMapping
    public ResponseEntity<ResourceListSupport<PaymentEntity>> findAll() {
        ResourceListSupport<PaymentEntity> resource = findAllPaymentUseCase
                .execute(() -> withSelf(linked().findAll()));
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDataSupport<PaymentEntity>> findOne(@PathVariable("id") String id) {
        ResourceDataSupport<PaymentEntity> resource = findOnePaymentUseCase
                .execute(id, () -> withSelf(linked().findOne(id)));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<ResourceDataSupport<PaymentEntity>> create(@RequestBody PaymentDTO payment) {
        ResourceDataSupport<PaymentEntity> resource = resource = createPaymentUseCase
                .execute(payment, () -> withSelf(linked().create(payment)));
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDataSupport<PaymentEntity>> update(@PathVariable("id") String id, @RequestBody PaymentDTO payment) {
        ResourceDataSupport<PaymentEntity> resource = resource = updatePaymentUseCase
                .execute(id, payment, () -> withSelf(linked().update(id, payment)));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceSupport> remove(@PathVariable("id") String id) {
        try {
            ResourceSupport resource = removePaymentUseCase
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

    @PostMapping("/{id}/refund")
    public ResponseEntity<ResourceDTOSupport<RefundPaymentDTO>> refund(@PathVariable("id") String id, @RequestBody RequestRefundDTO refund) {
        ResourceDTOSupport<RefundPaymentDTO> resource = refundPaymentUseCase.execute(
                id, refund, () -> withSelf(linked().refund(id, refund)));
        return ResponseEntity.ok(resource);
    }

}