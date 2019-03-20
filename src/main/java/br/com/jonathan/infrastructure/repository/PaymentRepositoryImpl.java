package br.com.jonathan.infrastructure.repository;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.repository.PaymentRepository;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import br.com.jonathan.infrastructure.repository.data.PaymentDataRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    @Inject
    private PaymentDataRepository repository;

    @Override
    public List<PaymentEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public PaymentEntity findOne(String id) {
        return repository.findById(UUID.fromString(id))
                .orElse(null);
    }

    @Override
    public PaymentEntity save(PaymentEntity payment) {
        return repository.save(payment);
    }

    @Override
    public PaymentEntity update(PaymentEntity payment) {
        return repository.save(payment);
    }

    @Override
    public void delete(String id) throws EntityNotFoundException {
        try {
            repository.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(e);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public boolean existsByOrderId(String id) {
        return repository.existsByOrderId(UUID.fromString(id));
    }
}
