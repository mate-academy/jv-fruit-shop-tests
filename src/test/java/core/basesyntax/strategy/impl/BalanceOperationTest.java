package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String OPERATION_CODE = "b";
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private StorageService storageService;
    private BalanceOperation balanceOperation;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void handle_validTransaction_Ok() {
        Transaction transaction = new Transaction(OPERATION_CODE, FRUIT_NAME, QUANTITY);
        balanceOperation.handle(transaction);
        assertEquals(QUANTITY, storageService.get(FRUIT_NAME));
    }
}
