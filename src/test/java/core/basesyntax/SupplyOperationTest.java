package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static Storage storage;
    private static OperationHandler supplyOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void handle_balanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 40);
        supplyOperation.handle(transaction, storage);
        Assertions.assertEquals(40, storage.getInventory().get("banana"));
    }
}
