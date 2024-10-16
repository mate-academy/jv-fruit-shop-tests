package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.StorageService;
import core.basesyntax.db.StorageServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private StorageService storageService;
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        storageService = new StorageServiceImpl();
        supplyOperationHandler = new SupplyOperationHandler(storageService);
    }

    @Test
    public void handle_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setAmount(5);

        Map<String, Integer> storage = new HashMap<>();

        supplyOperationHandler.handle(transaction, storage);
        assertEquals(5, storageService.getStorage().get("apple").intValue());
    }

    @Test
    public void handle_negativeAmount_NotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setAmount(-3);

        Map<String, Integer> storage = new HashMap<>();

        supplyOperationHandler.handle(transaction, storage);
        assertEquals(-3, storageService.getStorage()
                .getOrDefault("apple", 0).intValue());
    }
}
