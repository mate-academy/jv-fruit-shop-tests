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

class ReturnOperationHandlerTest {
    private StorageService storageService;
    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        returnOperationHandler = new ReturnOperationHandler(storageService);
    }

    @Test
    public void handle_Ok() {
        storageService.updateBalance("apple", 3);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setAmount(3);

        Map<String, Integer> storage = new HashMap<>();
        returnOperationHandler.handle(transaction, storage);

        assertEquals(6, storageService.getStorage().get("apple").intValue());
    }

    @Test
    public void handle_NonExistentFruit_NotOk() {
        // Arrange
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setAmount(10);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.handle(transaction, storageService.getStorage()));

        assertEquals("Cannot return fruit that is not in stock.", exception.getMessage());
    }
}
