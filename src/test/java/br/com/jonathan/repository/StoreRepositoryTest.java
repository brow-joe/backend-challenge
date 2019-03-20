package br.com.jonathan.repository;

import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.repository.StoreRepository;
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
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class StoreRepositoryTest extends Assertions {
    private static final Logger logger = LogManager.getLogger(StoreRepositoryTest.class);
    private static final Integer NAME_SIZEOF = 20;
    private static final Integer ADDRESS_SIZEOF = 50;

    @Inject
    private StoreRepository repository;

    @Mock
    private Supplier<StoreEntity> builder;

    @BeforeEach
    public void before() {
        Mockito.when(builder.get())
                .thenReturn(
                        new StoreEntity()
                                .setName(RandomStringUtils.randomAlphabetic(NAME_SIZEOF))
                                .setAddress(RandomStringUtils.randomAlphabetic(ADDRESS_SIZEOF))
                );
    }

    @AfterEach
    public void after() {
        repository.deleteAll();
    }

    @DisplayName("Store repository")
    @Test
    public void test() throws Exception {
        assertNotNull(repository);
        assertNotNull(builder);
        StoreEntity store = builder.get();
        assertNotNull(store);

        StoreEntity created = create(store);

        int sizeOf = (int) (Math.random() * 100);
        String newAddress = RandomStringUtils.randomAlphabetic(sizeOf);
        created.setAddress(newAddress);

        StoreEntity updated = update(created);
        assertEquals(updated.getAddress(), newAddress);

        UUID storeId = updated.getId();
        StoreEntity finder = find(storeId);
        assertNotNull(finder);
        List<StoreEntity> data = find();
        assertTrue(CollectionUtils.isNotEmpty(data));

        remove(storeId);
        finder = find(storeId);
        assertNull(finder);
        data = find();
        assertTrue(CollectionUtils.isEmpty(data));
    }

    private List<StoreEntity> find() {
        return repository.findAll();
    }

    private StoreEntity find(UUID storeId) {
        assertNotNull(storeId);
        return repository.findOne(storeId.toString());
    }

    private void remove(UUID storeId) throws Exception {
        assertNotNull(storeId);
        repository.delete(storeId.toString());
        logger.info("store has been removed!");
    }

    private StoreEntity update(StoreEntity store) {
        StoreEntity updated = repository.update(store);
        assertNotNull(updated);
        logger.info("store has been updated!");

        assertTrue(StringUtils.isNotEmpty(updated.getName()));
        assertTrue(StringUtils.isNotEmpty(updated.getAddress()));

        assertEquals(NAME_SIZEOF, updated.getName().length());
        assertNotEquals(ADDRESS_SIZEOF, updated.getAddress().length());
        return updated;
    }

    private StoreEntity create(StoreEntity store) {
        StoreEntity created = repository.save(store);
        assertNotNull(created);
        logger.info("store has been created!");

        assertEquals(NAME_SIZEOF, created.getName().length());
        assertEquals(ADDRESS_SIZEOF, created.getAddress().length());
        return created;
    }

}
