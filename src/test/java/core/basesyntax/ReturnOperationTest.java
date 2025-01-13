package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static Storage storage;
    private static OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        storage.set("banana", 50);
        returnOperation = new ReturnOperation();
    }

    @Test
    void handle_balanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 10);
        returnOperation.handle(transaction, storage);
        Assertions.assertEquals(60, storage.getInventory().get("banana"));
    }
}
