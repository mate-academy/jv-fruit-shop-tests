package core.basesyntax.operation.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.AdditionHandlerImpl;
import core.basesyntax.service.operation.Handler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdditionHandlerImplTest {
    private static Handler addHandler;

    @BeforeClass
    public static void setUp() {
        addHandler = new AdditionHandlerImpl();
    }

    @Test
    public void handle_validDataReturnAndSupply_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 10);
        FruitStorage.fruitStorage.clear();
        FruitStorage.fruitStorage.put("banana", 25);
        int expected = 35;
        addHandler.handle(fruitTransaction);
        int actual = FruitStorage.fruitStorage.get("banana");
        Assert.assertEquals(expected, actual);
    }
}
