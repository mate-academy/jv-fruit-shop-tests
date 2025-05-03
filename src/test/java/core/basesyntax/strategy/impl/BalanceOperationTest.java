package core.basesyntax.strategy.impl;

import static org.mockito.Mockito.verify;

import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    @Mock
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        balanceOperation = new BalanceOperation(storageService);
    }

    @Test
    public void testHandle() {
        FruitsTransaction transaction = new FruitsTransaction("b", "Apple", 10);
        balanceOperation.handle(transaction);
        verify(storageService).add("Apple", 10);
    }
}
