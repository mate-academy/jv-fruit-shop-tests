package core.basesyntax.strategyimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BuyOperationHandler;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BuyOperationHandlerTest {
    private static Map<String, Integer> fruitStorage;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new BuyOperationHandler();
        fruitStorage = Storage.fruit;
        fruitStorage.put("apple", 35);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        fruitStorage.clear();
    }

    @Test
    public void buy_validOperation_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 15);
        operationHandler.handle(fruitTransaction);
        int actual = fruitStorage.get("apple");
        int expected = 20;
        assertEquals(expected,actual);
    }

    @Test
    public void buy_zeroValue_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 0);
        operationHandler.handle(fruitTransaction);
        int actual = fruitStorage.get("apple");
        int expected = 35;
        assertEquals(expected,actual);
    }
}
