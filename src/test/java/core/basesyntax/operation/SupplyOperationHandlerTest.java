package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.operation.SupplyOperationHandler;
import core.basesyntax.strorage.FruitStorage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyOperationHandler();
    }

    @Before
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void apply_fullStorage_ok() {
        FruitStorage.fruits.put("banana", 100);
        FruitStorage.fruits.put("apple", 70);
        supplyHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 80));
        supplyHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30));
        int actualBanana = FruitStorage.fruits.get("banana");
        int expectedBanana = 180;
        int actualApple = FruitStorage.fruits.get("apple");
        int expectedApple = 100;
        assertEquals("Not valid apple quantity", expectedApple, actualApple);
        assertEquals("Not valid banana quantity", expectedBanana, actualBanana);
    }

    @Test
    public void apply_emptyStorage_ok() {
        supplyHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 80));
        supplyHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30));
        int actualBanana = FruitStorage.fruits.get("banana");
        int expectedBanana = 80;
        int actualApple = FruitStorage.fruits.get("apple");
        int expectedApple = 30;
        assertEquals("Not valid apple quantity", expectedApple, actualApple);
        assertEquals("Not valid banana quantity", expectedBanana, actualBanana);
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullValue_notOk() {
        supplyHandler.apply(null);
    }
}
