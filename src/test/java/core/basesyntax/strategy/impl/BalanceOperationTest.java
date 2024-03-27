package core.basesyntax.strategy.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        storageService = mock(StorageService.class);
        balanceOperation = new BalanceOperation(storageService);
    }

    @Test
    public void testHandle() {
        FruitsTransaction transaction = new FruitsTransaction("b", "Apple", 10);
        balanceOperation.handle(transaction);
        Mockito.verify(storageService, times(1)).add("Apple", 10);
    }
}
