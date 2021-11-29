package strategy;

import db.Storage;
import java.util.Map;
import model.Fruit;
import model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operator;

    @BeforeClass
    public static void beforeAll() {
        operator = new BalanceOperationHandler();
    }

    @Test
    public void apply_addNewFruit_ok() {
        TransactionDto transactionDto = new TransactionDto("b","banana", 100);
        Fruit fruit = new Fruit(transactionDto.getFruitName());
        Map<Fruit, Integer> actual = Storage.storage;
        Map<Fruit, Integer> expected = actual;
        actual.put(fruit, transactionDto.getQuantity());
        TransactionDto addNewFruit = new TransactionDto("b","apple", 90);
        expected.put(fruit, addNewFruit.getQuantity());
        operator.apply(addNewFruit);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
