package br.com.jonathan.application.pattern.specification.validation.payment;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.repository.PaymentRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class PaymentStatusRefundSpecification implements Specification<String> {
    private final List<PaymentTypeEnum> exclusion = Lists.newArrayList(PaymentTypeEnum.REFUNDED, PaymentTypeEnum.COMPLETED);

    @Inject
    private PaymentRepository repository;

    @Override
    public Predicate<String> predicate() {
        return id -> isValid(id);
    }

    private boolean isValid(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return isValid(repository.findOne(id));
        }
        return true;
    }

    private boolean isValid(PaymentEntity payment) {
        if (Objects.nonNull(payment) && Objects.nonNull(payment.getStatus())) {
            return !exclusion.contains(payment.getStatus());
        }
        return true;
    }

    @Override
    public Error getError() {
        return Error.of(10, "An order must only be refunded within 10 days after confirmation!");
    }

}