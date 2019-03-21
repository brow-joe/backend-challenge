package br.com.jonathan.domain.repository;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;

import java.util.List;

public interface PaymentRepository {

    public List<PaymentEntity> findAll();

    public PaymentEntity findOne(String id);

    public PaymentEntity save(PaymentEntity payment);

    public PaymentEntity update(PaymentEntity payment);

    public void delete(String id) throws EntityNotFoundException;

    public void deleteAll();

    public boolean existsByOrderId(String id);

    public boolean existsById(String id);

    public List<PaymentEntity> findAllByStatus(PaymentTypeEnum status);

}
