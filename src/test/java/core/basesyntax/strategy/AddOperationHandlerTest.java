package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationHandlerException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static TransactionDto transactionDto;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new AddOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test (expected = OperationHandlerException.class)
    public void apply_addMaxQuantityFruitIsExist_notOk() {
        Storage.fruits.put(new Fruit("pineapple"), 110);
        transactionDto = new TransactionDto("b", "pineapple", Integer.MAX_VALUE);
        operationHandler.apply(transactionDto);
    }

    @Test
    public void apply_addMaxQuantityFruitIsNotExist_ok() {
        transactionDto = new TransactionDto("b", "pineapple", Integer.MAX_VALUE);
        fruit = new Fruit(transactionDto.getFruitName());
        operationHandler.apply(transactionDto);
        assertTrue(Storage.fruits.containsKey(fruit));
        assertEquals(transactionDto.getQuantity(), (int) Storage.fruits.get(fruit));
    }

    @Test
    public void apply_addFruitIsNotExist_ok() {
        transactionDto = new TransactionDto("b", "pineapple", 20);
        fruit = new Fruit(transactionDto.getFruitName());
        operationHandler.apply(transactionDto);
        assertTrue(Storage.fruits.containsKey(fruit));
        assertEquals(transactionDto.getQuantity(), (int) Storage.fruits.get(fruit));
    }

    @Test
    public void apply_addFruitIsExist_ok() {
        Storage.fruits.put(new Fruit("apple"), 100);
        Storage.fruits.put(new Fruit("banana"), 25);
        transactionDto = new TransactionDto("b", "banana", 100);
        fruit = new Fruit(transactionDto.getFruitName());
        operationHandler.apply(transactionDto);
        assertTrue(Storage.fruits.containsKey(fruit));
        assertEquals(transactionDto.getQuantity() + 25, (int) Storage.fruits.get(fruit));
    }
}
