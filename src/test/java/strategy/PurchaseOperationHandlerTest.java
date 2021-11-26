package strategy;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import model.TransactionDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operator;
    private static Map<Fruit, Integer> expected;
    private static Fruit fruit;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void beforeAll() {
        fruit = new Fruit("banana");
        operator = new PurchaseOperationHandler();
        actual = Storage.storage;
        actual.put(fruit, 100);
        expected = new HashMap<>();
        expected.put(fruit, 100);
    }

    @Test
    public void apply_operationR_ok() {
        TransactionDto transactionDto = new TransactionDto("r","banana", 20);
        expected.put(fruit,actual.get(fruit) - transactionDto.getQuantity());
        operator.apply(transactionDto);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_operationR_notOk() {
        Storage.storage.put(fruit, 100);
        TransactionDto transactionDto = new TransactionDto("r","banana", 120);
        operator.apply(transactionDto);
    }
}
