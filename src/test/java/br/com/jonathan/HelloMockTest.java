package br.com.jonathan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.function.Supplier;

@ActiveProfiles("test")
@SpringBootTest
public class HelloMockTest extends Assertions {

    @Mock
    private Supplier<String> supplier;

    @BeforeEach
    private void before() {
        Mockito.when(supplier.get()).thenReturn(UUID.randomUUID().toString());
    }

    @DisplayName("Hello test")
    @Test
    public void test() {
        assertNotNull(supplier);
        assertNotNull(supplier.get());
    }

}
