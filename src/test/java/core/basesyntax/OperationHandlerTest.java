package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.ProductNotFoundException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.operation.BalanceOperationHandler;
import core.basesyntax.strategy.impl.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.operation.ReturnOperationHandler;
import core.basesyntax.strategy.impl.operation.SupplyOperationHandler;
import core.basesyntax.strorage.FruitStorage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static OperationHandler balanceHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler returnHandler;
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceOperationHandler();
        purchaseHandler = new PurchaseOperationHandler();
        returnHandler = new ReturnOperationHandler();
        supplyHandler = new SupplyOperationHandler();
    }

    @Before
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void apply_balanceOperation_ok() {
        balanceHandler.apply(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        balanceHandler.apply(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 80));
        int actualApple = FruitStorage.fruits.get("apple");
        int expectedBanana = 20;
        int expectedApple = 80;
        int actualBanana = FruitStorage.fruits.get("banana");
        assertEquals("Not valid banana quantity", actualBanana, expectedBanana);
        assertEquals("Not valid apple quantity",actualApple, expectedApple);
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullValue_notOk() {
        balanceHandler.apply(null);
    }

    @Test
    public void apply_supplyOperationFullStorage_ok() {
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
    public void apply_supplyOperationEmptyStorage_ok() {
        supplyHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 80));
        supplyHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30));
        int actualBanana = FruitStorage.fruits.get("banana");
        int expectedBanana = 80;
        int actualApple = FruitStorage.fruits.get("apple");
        int expectedApple = 30;
        assertEquals("Not valid apple quantity", expectedApple, actualApple);
        assertEquals("Not valid banana quantity", expectedBanana, actualBanana);
    }

    @Test
    public void apply_purchaseOperationValid_ok() {
        FruitStorage.fruits.put("banana", 100);
        purchaseHandler.apply(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 80));
        int actual = FruitStorage.fruits.get("banana");
        int expected = 20;
        assertEquals("Buyers purchased the product, balance: ", expected, actual);
    }

    @Test(expected = ProductNotFoundException.class)
    public void apply_purchaseOperationNotValidQuantity_ok() {
        FruitStorage.fruits.put("banana", 50);
        purchaseHandler.apply(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 80));
    }

    @Test(expected = ProductNotFoundException.class)
    public void apply_purchaseOperationNotFoundFruit_ok() {
        FruitStorage.fruits.put("banana", 50);
        purchaseHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 80));
    }

    @Test
    public void apply_returnNotValid_ok() {
        FruitStorage.fruits.put("apple", 50);
        returnHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10));
        int expected = 60;
        int actual = FruitStorage.fruits.get("apple");
        assertEquals("Buyer return some fruits, balance: ", expected, actual);
    }
}
