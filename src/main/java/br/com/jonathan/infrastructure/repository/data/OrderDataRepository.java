package br.com.jonathan.infrastructure.repository.data;

import br.com.jonathan.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDataRepository extends JpaRepository<OrderEntity, UUID> {

    public boolean existsByStoreId(UUID storeId);

}
