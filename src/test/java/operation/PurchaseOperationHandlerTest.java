package operation;

import db.Storage;
import model.Fruit;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private final OperationHandler operationHandler;
    private final Fruit fruit;
    private final FruitTransaction fruitTransaction;

    private PurchaseOperationHandlerTest() {
        this.operationHandler = new PurchaseOperationHandler();
        this.fruit = new Fruit("apple");
        this.fruitTransaction = new FruitTransaction("b", fruit, 10);
    }

    @Test
    void purchase_updateAmount_ok() {
        Storage.data.put(fruit, 15);
        operationHandler.process(fruitTransaction);
        int expected = 5;
        int actual = Storage.data.get(fruit);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void purchase_updateAmount_notOk() {
        Storage.data.put(fruit, 5);
        Assertions.assertThrows(RuntimeException.class,
                () -> operationHandler.process(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.data.clear();
    }
}
