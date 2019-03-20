package br.com.jonathan.repository;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import br.com.jonathan.domain.repository.OrderRepository;
import br.com.jonathan.domain.repository.PaymentRepository;
import br.com.jonathan.domain.repository.StoreRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
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
public class PaymentRepositoryTest extends Assertions {
    private static final Logger logger = LogManager.getLogger(PaymentRepositoryTest.class);
    private static final String CREDIT_CARD = UUID.randomUUID().toString();
    private static final Date DATE = new Date();
    private static final PaymentTypeEnum STATUS = null;

    @Inject
    private StoreRepository storeRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private PaymentRepository paymentRepository;

    @Mock
    private Supplier<PaymentEntity> builder;

    @BeforeEach
    public void before() {
        UUID storeId = storeRepository.save(
                new StoreEntity()
                        .setName(RandomStringUtils.randomAlphabetic((int) (Math.random() * 20)))
                        .setAddress(RandomStringUtils.randomAlphabetic((int) (Math.random() * 50)))
        ).getId();

        UUID orderId = orderRepository.save(
                new OrderEntity()
                        .setAddress(RandomStringUtils.randomAlphabetic((int) (Math.random() * 60)))
                        .setConfirmation(new Date())
                        .setStatus(null)
                        .setStore(new StoreEntity(storeId))
        ).getId();

        Mockito.when(builder.get())
                .thenReturn(
                        new PaymentEntity()
                                .setCreditCard(CREDIT_CARD)
                                .setStatus(STATUS)
                                .setPayment(DATE)
                                .setOrder(new OrderEntity(orderId))
                );
    }

    @AfterEach
    public void after() {
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @DisplayName("Payment repository")
    @Test
    public void test() throws Exception {
        assertNotNull(orderRepository);
        assertNotNull(builder);
        PaymentEntity payment = builder.get();
        assertNotNull(payment);

        PaymentEntity created = create(payment);

        String newCreditCard = UUID.randomUUID().toString();
        created.setCreditCard(newCreditCard);

        PaymentEntity updated = update(created);
        assertEquals(updated.getCreditCard(), newCreditCard);

        UUID paymentId = updated.getId();
        PaymentEntity finder = find(paymentId);
        assertNotNull(finder);
        List<PaymentEntity> data = find();
        assertTrue(CollectionUtils.isNotEmpty(data));

        remove(paymentId);
        finder = find(paymentId);
        assertNull(finder);
        data = find();
        assertTrue(CollectionUtils.isEmpty(data));
    }

    private List<PaymentEntity> find() {
        return paymentRepository.findAll();
    }

    private PaymentEntity find(UUID paymentId) {
        assertNotNull(paymentId);
        return paymentRepository.findOne(paymentId.toString());
    }

    private void remove(UUID paymentId) throws Exception {
        assertNotNull(paymentId);
        paymentRepository.delete(paymentId.toString());
        logger.info("payment has been removed!");
    }

    private PaymentEntity update(PaymentEntity payment) {
        PaymentEntity updated = paymentRepository.update(payment);
        assertNotNull(updated);
        logger.info("payment has been updated!");

        assertNotEquals(CREDIT_CARD, updated.getCreditCard());
        assertEquals(DATE, updated.getPayment());
        assertEquals(STATUS, updated.getStatus());
        return updated;
    }

    private PaymentEntity create(PaymentEntity payment) {
        PaymentEntity created = paymentRepository.save(payment);
        assertNotNull(created);
        logger.info("payment has been created!");

        assertEquals(CREDIT_CARD, created.getCreditCard());
        assertEquals(DATE, created.getPayment());
        assertEquals(STATUS, created.getStatus());
        return created;
    }

}
