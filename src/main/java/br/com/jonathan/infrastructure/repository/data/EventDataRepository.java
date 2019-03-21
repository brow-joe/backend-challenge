package br.com.jonathan.infrastructure.repository.data;

import br.com.jonathan.domain.entity.EventEntity;
import br.com.jonathan.domain.enumeration.EventTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventDataRepository extends JpaRepository<EventEntity, UUID> {

    public List<EventEntity> findAllByEvent(EventTypeEnum event);

}
