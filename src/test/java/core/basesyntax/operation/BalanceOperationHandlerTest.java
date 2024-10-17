package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.StorageService;
import core.basesyntax.db.StorageServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private StorageService storageService;
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        balanceOperationHandler = new BalanceOperationHandler(storageService);
    }

    @Test
    public void handle_Ok() {
        FruitTransaction transaction = new FruitTransaction();

        transaction.setFruit("apple");
        transaction.setAmount(10);

        Map<String, Integer> storage = new HashMap<>();

        balanceOperationHandler.handle(transaction, storage);

        assertEquals(10, storageService.getStorage().get("apple").intValue());
    }

    @Test
    public void handle_negativeAmount_NotOk() {
        FruitTransaction transaction = new FruitTransaction();

        transaction.setFruit("apple");
        transaction.setAmount(-4);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> balanceOperationHandler
                        .handle(transaction, storageService.getStorage()));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
}
