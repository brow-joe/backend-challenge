package br.com.jonathan.domain.repository;

import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;

import java.util.List;

public interface StoreRepository {

    public List<StoreEntity> findAll();

    public StoreEntity findOne(String id);

    public StoreEntity save(StoreEntity store);

    public StoreEntity update(StoreEntity store);

    public void delete(String id) throws EntityNotFoundException;

    public void deleteAll();

    public boolean existsById(String id);

}
