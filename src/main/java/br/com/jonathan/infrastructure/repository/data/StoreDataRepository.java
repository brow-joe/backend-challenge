package br.com.jonathan.infrastructure.repository.data;

import br.com.jonathan.domain.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreDataRepository extends JpaRepository<StoreEntity, UUID> {
}
