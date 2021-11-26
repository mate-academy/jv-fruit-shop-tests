package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
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

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_addFruitNotExist_ok() {
        transactionDto = new TransactionDto("b", "banana", 50);
        fruit = new Fruit(transactionDto.getFruitName());
        operationHandler.apply(transactionDto);
        int expectedQuantity = transactionDto.getQuantity();
        int actualQuantity = Storage.storage.get(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void apply_addFruitIsExist_ok() {
        Storage.storage.put(new Fruit("banana"), 20);
        transactionDto = new TransactionDto("s", "banana", 100);
        operationHandler.apply(transactionDto);
        fruit = new Fruit(transactionDto.getFruitName());
        int expectedQuantity = 120;
        int actualQuantity = Storage.storage.get(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }
}
