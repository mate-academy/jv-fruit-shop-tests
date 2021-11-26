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
    private static TransactionDto transactionDto = new TransactionDto("b","banana", 100);
    private static OperationHandler operator = new BalanceOperationHandler();
    private static Fruit fruit = new Fruit(transactionDto.getFruitName());
    private static Map<Fruit, Integer> actual = Storage.storage;
    private static Map<Fruit, Integer> expected = actual;

    @BeforeClass
    public static void beforeAll() {
        actual.put(fruit, transactionDto.getQuantity());
    }

    @Test
    public void apply_addNewFruit_ok() {
        TransactionDto transactionDto = new TransactionDto("b","apple", 90);
        expected.put(fruit, transactionDto.getQuantity());
        operator.apply(transactionDto);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
