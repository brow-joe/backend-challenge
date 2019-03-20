package br.com.jonathan.infrastructure.repository.data;

import br.com.jonathan.domain.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentDataRepository extends JpaRepository<PaymentEntity, UUID> {
}
