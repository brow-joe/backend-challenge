package br.com.jonathan.domain.repository;

import br.com.jonathan.domain.entity.EventEntity;
import br.com.jonathan.domain.enumeration.EventTypeEnum;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;

import java.util.List;

public interface EventRepository {

    public EventEntity save(EventEntity event);

    public List<EventEntity> findAllByEvent(EventTypeEnum event);

    public void delete(String id) throws EntityNotFoundException;

}
