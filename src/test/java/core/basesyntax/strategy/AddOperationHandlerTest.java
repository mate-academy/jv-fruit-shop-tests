package core.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class AddOperationHandlerTest {
    private OperationHandler addOperation = new AddOperationHandler();

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = NullPointerException.class)
    public void getNullTransaction_NotOk() {
        addOperation.apply(null);
    }

    @Test
    public void addOkNumber_Ok() {
        add("coconut", 20, 20);
    }

    @Test
    public void addZeroNumber_Ok() {
        add("coconut", 0, 0);
    }

    private void add(String fruitName, int quantity, int expected) {
        Fruit fruit = new Fruit(fruitName);
        Transaction transaction = new Transaction("a", fruit.getName(), quantity);
        addOperation.apply(transaction);
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
