package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String OPERATION_CODE = "p";
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private static final int INITIAL_QUANTITY = 20;
    private StorageService storageService;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void handle_validTransaction_Ok() {
        storageService.add(FRUIT_NAME, INITIAL_QUANTITY);
        Transaction transaction = new Transaction(OPERATION_CODE, FRUIT_NAME, QUANTITY);
        purchaseOperation.handle(transaction);

        int expected = INITIAL_QUANTITY - QUANTITY;
        assertEquals(expected, storageService.get(FRUIT_NAME));
    }

    @Test
    void handle_invalidTransaction_NotOk() {
        storageService.add(FRUIT_NAME, QUANTITY);
        Transaction transaction = new Transaction(OPERATION_CODE, FRUIT_NAME, INITIAL_QUANTITY);
        var exception = assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(transaction));

        String expected = "Insufficient quantity in stock. Cannot purchase "
                + INITIAL_QUANTITY + " items when only " + QUANTITY + " items are available.";
        assertEquals(expected, exception.getMessage());
    }
}
