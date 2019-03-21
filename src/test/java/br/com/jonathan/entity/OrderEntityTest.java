package br.com.jonathan.entity;

import br.com.jonathan.application.dto.OrderDTO;
import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.enumeration.OrderTypeEnum;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class OrderEntityTest extends Assertions {

    @DisplayName("Order entity")
    @Test
    public void testNotValid() throws Exception {
        OrderEntity order = new OrderEntity();
        assertFalse(order.isValid());
    }

    @DisplayName("Order entity")
    @Test
    public void testIsValid() throws Exception {
        OrderEntity order = new OrderEntity()
                .setAddress(UUID.randomUUID().toString())
                .setStatus(OrderTypeEnum.CREATED);
        assertTrue(order.isValid());
    }

    @DisplayName("Order entity")
    @Test
    public void test() throws Exception {
        OrderEntity order = new OrderEntity()
                .setAddress(UUID.randomUUID().toString())
                .setStatus(OrderTypeEnum.CREATED)
                .setConfirmation(new Date());

        OrderDTO dto = order.toDTO();
        assertNotNull(dto);
        assertEquals(dto.getAddress(), order.getAddress());
        assertEquals(dto.getStatus(), order.getStatus());

        assertFalse(order.hasCompleted(new DateTime(new Date()).plusDays(1).toDate()));
        assertTrue(order.hasCompleted(new DateTime(new Date()).plusDays(20).toDate()));
    }

}
