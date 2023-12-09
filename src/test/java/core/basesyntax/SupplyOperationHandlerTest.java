package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;
    private static Storage storage;

    @BeforeAll
    public static void setSupplyOperationHandler() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @BeforeEach
    public void setStorage() {
        storage = new StorageImpl();
        storage.put("banana", 234);
        storage.put("apple", 327);
    }

    @Test
    public void process_validData_Ok() {
        final FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                3);
        Storage expectedStorage = new StorageImpl();
        expectedStorage.put("banana", 234);
        expectedStorage.put("apple", 330);
        supplyOperationHandler.process(fruitTransaction, storage);
        assertEquals(expectedStorage, storage);
    }
}
