package br.com.jonathan.entity;

import br.com.jonathan.application.dto.StoreDTO;
import br.com.jonathan.domain.entity.StoreEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class StoreEntityTest extends Assertions {

    @DisplayName("Store entity")
    @Test
    public void testNotValid() throws Exception {
        StoreEntity store = new StoreEntity();
        assertFalse(store.isValid());
    }

    @DisplayName("Store entity")
    @Test
    public void testIsValid() throws Exception {
        StoreEntity store = new StoreEntity()
                .setAddress(UUID.randomUUID().toString())
                .setName(UUID.randomUUID().toString());
        assertTrue(store.isValid());
    }

    @DisplayName("Store entity")
    @Test
    public void test() throws Exception {
        StoreEntity store = new StoreEntity()
                .setAddress(UUID.randomUUID().toString())
                .setName(UUID.randomUUID().toString());

        StoreDTO dto = store.toDTO();
        assertNotNull(dto);
        assertEquals(dto.getAddress(), store.getAddress());
        assertEquals(dto.getName(), store.getName());
    }

}
