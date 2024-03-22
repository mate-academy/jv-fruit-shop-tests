package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String OPERATION_CODE = "r";
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 1;
    private static final int INITIAL_QUANTITY = 20;
    private StorageService storageService;
    private ReturnOperation returnOperation;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        returnOperation = new ReturnOperation();
    }

    @Test
    void handle_validTransaction_Ok() {
        storageService.add(FRUIT_NAME, INITIAL_QUANTITY);
        Transaction transaction = new Transaction(OPERATION_CODE, FRUIT_NAME, QUANTITY);
        returnOperation.handle(transaction);
        int expected = INITIAL_QUANTITY + QUANTITY;
        assertEquals(expected, storageService.get(FRUIT_NAME));
    }
}
