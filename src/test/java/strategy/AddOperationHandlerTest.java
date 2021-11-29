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

    @BeforeClass
    public static void beforeAll() {
        operator = new AddOperationHandler();
    }

    @Test
    public void apply_operationS_ok() {
        TransactionDto addFruit = new TransactionDto("b","banana", 100);
        Fruit fruit = new Fruit(addFruit.getFruitName());
        Map<Fruit, Integer> expected = Storage.storage;
        Storage.storage.put(fruit, addFruit.getQuantity());
        TransactionDto transactionDto = new TransactionDto("s","banana", 20);
        expected.put(fruit, transactionDto.getQuantity() + Storage.storage.get(fruit));
        operator.apply(transactionDto);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test
    public void apply_operationP_ok() {
        TransactionDto addFruit = new TransactionDto("b","banana", 100);
        Fruit fruit = new Fruit(addFruit.getFruitName());
        Map<Fruit, Integer> expected = Storage.storage;
        Storage.storage.put(fruit, addFruit.getQuantity());
        TransactionDto transactionDto = new TransactionDto("p","banana", 10);
        expected.put(fruit, transactionDto.getQuantity() + Storage.storage.get(fruit));
        operator.apply(transactionDto);
        Assert.assertEquals(expected, Storage.storage);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
