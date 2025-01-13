package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static Storage storage;
    private static OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        storage.set("banana", 50);
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void handle_balanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 30);
        purchaseOperation.handle(transaction, storage);
        Assertions.assertEquals(20, storage.getInventory().get("banana"));
    }

}
