package strategy;

import db.Storage;
import java.util.Map;
import model.Fruit;
import model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler operator;
    private static Map<Fruit, Integer> expected;
    private static Fruit fruit;
    private static Map<Fruit, Integer> actual;
    private static TransactionDto transactionDto;

    @BeforeClass
    public static void beforeAll() {
        transactionDto = new TransactionDto("b","banana", 100);
        fruit = new Fruit(transactionDto.getFruitName());
        operator = new AddOperationHandler();
        actual = Storage.storage;
        actual.put(fruit, transactionDto.getQuantity());
        expected = actual;
    }

    @Test
    public void apply_addNewFruit_ok() {
        TransactionDto transactionDto = new TransactionDto("b","apple", 90);
        expected.put(fruit, transactionDto.getQuantity());
        operator.apply(transactionDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_operationS_ok() {
        Storage.storage.put(fruit, transactionDto.getQuantity());
        TransactionDto transactionDto = new TransactionDto("s","banana", 20);
        expected.put(fruit, transactionDto.getQuantity() + actual.get(fruit));
        operator.apply(transactionDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_operationP_ok() {
        TransactionDto transactionDto = new TransactionDto("p","banana", 10);
        expected.put(fruit, transactionDto.getQuantity() + actual.get(fruit));
        operator.apply(transactionDto);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
