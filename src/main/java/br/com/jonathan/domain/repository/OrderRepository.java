package br.com.jonathan.domain.repository;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;

import java.util.List;

public interface OrderRepository {

    public List<OrderEntity> findAll();

    public OrderEntity findOne(String id);

    public OrderEntity save(OrderEntity order);

    public OrderEntity update(OrderEntity order);

    public void delete(String id) throws EntityNotFoundException;

    public void deleteAll();

    public boolean existsByStoreId(String id);

    public boolean existsById(String id);

}
