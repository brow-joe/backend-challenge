package br.com.jonathan.repository;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.entity.OrderItemEntity;
import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.enumeration.OrderTypeEnum;
import br.com.jonathan.domain.repository.OrderRepository;
import br.com.jonathan.domain.repository.PaymentRepository;
import br.com.jonathan.domain.repository.StoreRepository;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class OrderRepositoryTest extends Assertions {
    private static final Logger logger = LogManager.getLogger(OrderRepositoryTest.class);
    private static final Integer ADDRESS_SIZEOF = 60;
    private static final Date DATE = new Date();
    private static final OrderTypeEnum STATUS = null;

    @Inject
    private StoreRepository storeRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private PaymentRepository paymentRepository;

    @Mock
    private Supplier<OrderEntity> builder;

    @BeforeEach
    public void before() {
        UUID storeId = storeRepository.save(
                new StoreEntity()
                        .setName(RandomStringUtils.randomAlphabetic((int) (Math.random() * 20)))
                        .setAddress(RandomStringUtils.randomAlphabetic((int) (Math.random() * 50)))
        ).getId();

        Mockito.when(builder.get())
                .thenReturn(
                        new OrderEntity()
                                .setAddress(RandomStringUtils.randomAlphabetic(ADDRESS_SIZEOF))
                                .setConfirmation(DATE)
                                .setStatus(STATUS)
                                .setStore(
                                        new StoreEntity(storeId)
                                )
                );
    }

    @AfterEach
    public void after() {
        orderRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @DisplayName("Order repository")
    @Test
    public void test() throws Exception {
        assertNotNull(orderRepository);
        assertNotNull(builder);
        OrderEntity order = builder.get();
        assertNotNull(order);

        OrderEntity created = create(order);

        int sizeOf = (int) (Math.random() * 150);
        String newAddress = RandomStringUtils.randomAlphabetic(sizeOf);
        created.setAddress(newAddress);
        created.setItems(Sets.newHashSet(
                new OrderItemEntity()
                        .setDescription(RandomStringUtils.randomAlphabetic((int) (Math.random() * 10)))
                        .setPrice(Math.random() * 100)
                        .setQuantity((long) (Math.random() * 5))
        ));

        OrderEntity updated = update(created);
        assertEquals(updated.getAddress(), newAddress);

        UUID orderId = updated.getId();
        OrderEntity finder = find(orderId);
        assertNotNull(finder);
        List<OrderEntity> data = find();
        assertTrue(CollectionUtils.isNotEmpty(data));

        remove(orderId);
        finder = find(orderId);
        assertNull(finder);
        data = find();
        assertTrue(CollectionUtils.isEmpty(data));
    }

    private List<OrderEntity> find() {
        return orderRepository.findAll();
    }

    private OrderEntity find(UUID orderId) {
        assertNotNull(orderId);
        return orderRepository.findOne(orderId.toString());
    }

    private void remove(UUID orderId) throws Exception {
        assertNotNull(orderId);
        orderRepository.delete(orderId.toString());
        logger.info("order has been removed!");
    }

    private OrderEntity update(OrderEntity order) {
        OrderEntity updated = orderRepository.update(order);
        assertNotNull(updated);
        logger.info("order has been updated!");

        assertTrue(StringUtils.isNotEmpty(updated.getAddress()));
        assertNotEquals(ADDRESS_SIZEOF, updated.getAddress());
        assertEquals(DATE, updated.getConfirmation());
        assertEquals(STATUS, updated.getStatus());
        assertTrue(CollectionUtils.isNotEmpty(updated.getItems()));
        assertFalse(paymentRepository.existsByOrderId(updated.getId().toString()));
        return updated;
    }

    private OrderEntity create(OrderEntity order) {
        OrderEntity created = orderRepository.save(order);
        assertNotNull(created);
        logger.info("order has been created!");

        assertEquals(ADDRESS_SIZEOF, created.getAddress().length());
        assertEquals(DATE, created.getConfirmation());
        assertEquals(STATUS, created.getStatus());
        assertTrue(CollectionUtils.isEmpty(created.getItems()));
        assertFalse(paymentRepository.existsByOrderId(created.getId().toString()));
        return created;
    }

}
