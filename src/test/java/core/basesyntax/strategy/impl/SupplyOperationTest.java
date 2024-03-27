package core.basesyntax.strategy.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        storageService = mock(StorageService.class);
        supplyOperation = new SupplyOperation(storageService);
    }

    @Test
    public void testHandle() {
        FruitsTransaction transaction = new FruitsTransaction("s", "Banana", 20);
        when(storageService.getQuantity("Banana")).thenReturn(10);
        supplyOperation.handle(transaction);
        Mockito.verify(storageService, times(1)).updateQuantity(eq("Banana"), eq(30));
    }
}
