package br.com.jonathan.infrastructure.repository;

import br.com.jonathan.domain.entity.EventEntity;
import br.com.jonathan.domain.enumeration.EventTypeEnum;
import br.com.jonathan.domain.repository.EventRepository;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import br.com.jonathan.infrastructure.repository.data.EventDataRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Repository
public class EventRepositoryImpl implements EventRepository {

    @Inject
    private EventDataRepository repository;

    @Override
    public EventEntity save(EventEntity event) {
        return repository.save(event);
    }

    @Override
    public List<EventEntity> findAllByEvent(EventTypeEnum event) {
        return repository.findAllByEvent(event);
    }

    @Override
    public void delete(String id) throws EntityNotFoundException {
        try {
            repository.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(e);
        }
    }
    
}
