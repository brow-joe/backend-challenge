package br.com.jonathan.infrastructure.repository.data;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentDataRepository extends JpaRepository<PaymentEntity, UUID> {

    public boolean existsByOrderId(UUID id);

    public List<PaymentEntity> findAllByStatus(PaymentTypeEnum status);

}
