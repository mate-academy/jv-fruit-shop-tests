package core.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler purchaseOperation = new PurchaseOperationHandler();

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = NullPointerException.class)
    public void getNullTransaction_NotOk() {
        purchaseOperation.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseNonEnough_NotOk() {
        purchase("gum", 20, 0);
    }

    @Test
    public void purchaseOkNumber_Ok() {
        Fruit coconut = new Fruit("coconut");
        Storage.storage.put(coconut, 100);
        purchase("coconut", 20, 80);
    }

    @Test
    public void purchaseZeroNumber_Ok() {
        Fruit pineapple = new Fruit("pineapple");
        Storage.storage.put(pineapple, 140);
        purchase("pineapple", 0, 140);
    }

    private void purchase(String fruitName, int quantity, int expected) {
        Fruit fruit = new Fruit(fruitName);
        Transaction transaction = new Transaction("p", fruit.getName(), quantity);
        purchaseOperation.apply(transaction);
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
