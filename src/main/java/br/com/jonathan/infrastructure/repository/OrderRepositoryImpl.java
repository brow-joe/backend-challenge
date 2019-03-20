package br.com.jonathan.infrastructure.repository;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.repository.OrderRepository;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import br.com.jonathan.infrastructure.repository.data.OrderDataRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Inject
    private OrderDataRepository repository;

    @Override
    public List<OrderEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public OrderEntity findOne(String id) {
        return repository.findById(UUID.fromString(id))
                .orElse(null);
    }

    @Override
    public OrderEntity save(OrderEntity order) {
        return repository.save(order);
    }

    @Override
    public OrderEntity update(OrderEntity order) {
        return repository.save(order);
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
    public boolean existsByStoreId(String id) {
        return repository.existsByStoreId(UUID.fromString(id));
    }
}
