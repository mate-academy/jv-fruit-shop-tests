package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationHandlerException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static TransactionDto transactionDto;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test (expected = OperationHandlerException.class)
    public void apply_purchaseFruitIsNotExist_notOk() {
        transactionDto = new TransactionDto("p", "banana", 20);
        operationHandler.apply(transactionDto);
    }

    @Test (expected = OperationHandlerException.class)
    public void apply_purchaseFruitsAreNotEnough_notOk() {
        Storage.fruits.put(new Fruit("banana"), 25);
        transactionDto = new TransactionDto("p", "banana", 50);
        operationHandler.apply(transactionDto);
    }

    @Test
    public void apply_purchaseStorageWithOneKindOfFruitsAreEnough_ok() {
        int quantity = 25;
        fruit = new Fruit("banana");
        Storage.fruits.put(fruit, quantity);
        transactionDto = new TransactionDto("p", "banana", 5);
        operationHandler.apply(transactionDto);
        assertEquals(quantity - transactionDto.getQuantity(), (int) Storage.fruits.get(fruit));
    }

    @Test
    public void apply_purchaseStorageWithDifferentFruitsAreEnough_ok() {
        Storage.fruits.put(new Fruit("apple"), 20);
        Storage.fruits.put(new Fruit("pineapple"), 10);
        Storage.fruits.put(new Fruit("orange"), 40);
        int quantity = 25;
        fruit = new Fruit("banana");
        Storage.fruits.put(fruit, quantity);
        transactionDto = new TransactionDto("p", "banana", 5);
        operationHandler.apply(transactionDto);
        assertEquals(quantity - transactionDto.getQuantity(), (int) Storage.fruits.get(fruit));
    }
}
