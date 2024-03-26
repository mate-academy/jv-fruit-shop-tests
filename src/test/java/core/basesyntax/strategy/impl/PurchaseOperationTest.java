package core.basesyntax.strategy.impl;

import core.basesyntax.exceptions.InvalidFruitException;
import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        storageService = mock(StorageService.class);
        purchaseOperation = new PurchaseOperation(storageService);
    }

    @Test
    public void testHandle_EnoughQuantity() {
        FruitsTransaction transaction = new FruitsTransaction("p", "Apple", 10);
        when(storageService.getQuantity("Apple")).thenReturn(15);

        purchaseOperation.handle(transaction);
        Mockito.verify(storageService, times(1)).updateQuantity(eq("Apple"), eq(5));
    }

    @Test
    public void testHandle_NotEnoughQuantity() {
        FruitsTransaction transaction = new FruitsTransaction("p", "Apple", 10);
        when(storageService.getQuantity("Apple")).thenReturn(5);
        assertThrows(InvalidFruitException.class, () -> {
            purchaseOperation.handle(transaction);
        });
        verify(storageService, never()).updateQuantity(anyString(), anyInt());
    }
}
