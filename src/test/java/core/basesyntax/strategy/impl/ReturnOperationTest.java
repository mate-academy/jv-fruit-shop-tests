package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        storageService = mock(StorageService.class);
        returnOperation = new ReturnOperation(storageService);
    }

    @Test
    public void testHandle() {
        FruitsTransaction transaction = new FruitsTransaction("r", "Apple", 10);
        when(storageService.getQuantity("Apple")).thenReturn(5);
        returnOperation.handle(transaction);
        verify(storageService, times(1)).updateQuantity(eq("Apple"), eq(15));
    }
}
