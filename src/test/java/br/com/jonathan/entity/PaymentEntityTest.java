package br.com.jonathan.entity;

import br.com.jonathan.application.dto.PaymentDTO;
import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class PaymentEntityTest extends Assertions {

    @DisplayName("Payment entity")
    @Test
    public void testNotValid() throws Exception {
        PaymentEntity payment = new PaymentEntity();
        assertFalse(payment.isValid());
    }

    @DisplayName("Payment entity")
    @Test
    public void testIsValid() throws Exception {
        PaymentEntity payment = new PaymentEntity()
                .setStatus(PaymentTypeEnum.CREATED)
                .setCreditCard(UUID.randomUUID().toString());
        assertTrue(payment.isValid());
    }

    @DisplayName("Payment entity")
    @Test
    public void test() throws Exception {
        PaymentEntity payment = new PaymentEntity()
                .setStatus(PaymentTypeEnum.CREATED)
                .setCreditCard(UUID.randomUUID().toString());

        PaymentDTO dto = payment.toDTO();
        assertNotNull(dto);
        assertEquals(dto.getStatus(), payment.getStatus());
        assertEquals(dto.getCreditCard(), payment.getCreditCard());
    }

}
