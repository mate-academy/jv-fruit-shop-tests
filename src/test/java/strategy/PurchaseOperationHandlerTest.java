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

    @BeforeClass
    public static void beforeAll() {
        operator = new PurchaseOperationHandler();
    }

    @Test
    public void apply_operationR_ok() {
        Fruit fruit = new Fruit("banana");
        Map<Fruit, Integer> expected = new HashMap<>();
        Map<Fruit, Integer> actual = Storage.storage;
        actual.put(fruit, 100);
        expected.put(fruit, 100);
        TransactionDto transactionDto = new TransactionDto("r","banana", 20);
        expected.put(fruit,actual.get(fruit) - transactionDto.getQuantity());
        operator.apply(transactionDto);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_operationR_notOk() {
        Fruit fruit = new Fruit("banana");
        Storage.storage.put(fruit, 100);
        TransactionDto transactionDto = new TransactionDto("r","banana", 120);
        operator.apply(transactionDto);
    }
}
