package br.com.jonathan.infrastructure.repository;

import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.repository.StoreRepository;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import br.com.jonathan.infrastructure.repository.data.StoreDataRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Repository
public class StoreRepositoryImpl implements StoreRepository {

    @Inject
    private StoreDataRepository repository;

    @Override
    public List<StoreEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public StoreEntity findOne(String id) {
        return repository.findById(UUID.fromString(id))
                .orElse(null);
    }

    @Override
    public StoreEntity save(StoreEntity store) {
        return repository.save(store);
    }

    @Override
    public StoreEntity update(StoreEntity store) {
        return repository.save(store);
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
    public boolean existsById(String id) {
        return repository.existsById(UUID.fromString(id));
    }

}
