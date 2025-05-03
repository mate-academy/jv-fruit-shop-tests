package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String OPERATION_CODE = "s";
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private static final int INITIAL_QUANTITY = 20;
    private StorageService storageService;
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void handle_validTransaction_Ok() {
        storageService.add(FRUIT_NAME, INITIAL_QUANTITY);
        Transaction transaction = new Transaction(OPERATION_CODE, FRUIT_NAME, QUANTITY);
        int expected = INITIAL_QUANTITY + QUANTITY;

        supplyOperation.handle(transaction);
        assertEquals(expected, storageService.get(FRUIT_NAME));
    }
}
