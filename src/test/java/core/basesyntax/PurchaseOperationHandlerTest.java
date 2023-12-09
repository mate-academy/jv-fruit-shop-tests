package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private static Storage storage;

    @BeforeAll
    public static void setPurchaseOperationHandler() {
        purchaseOperationHandler = new PurchaseOperationHandler();
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
                FruitTransaction.Operation.PURCHASE,
                "banana",
                34);
        Storage expectedStorage = new StorageImpl();
        expectedStorage.put("banana", 200);
        expectedStorage.put("apple", 327);
        purchaseOperationHandler.process(fruitTransaction, storage);
        assertEquals(expectedStorage, storage);
    }
}
