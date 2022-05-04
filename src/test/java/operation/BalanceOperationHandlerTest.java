package operation;

import db.Storage;
import model.Fruit;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private final OperationHandler operationHandler;
    private final Fruit fruit;
    private final FruitTransaction fruitTransaction;

    private BalanceOperationHandlerTest() {
        this.operationHandler = new BalanceOperationHandler();
        this.fruit = new Fruit("apple");
        this.fruitTransaction = new FruitTransaction("b", fruit, 8);
    }

    @Test
    void balance_byCorrectAmount_ok() {
        operationHandler.process(fruitTransaction);
        int expected = 8;
        int actual = Storage.data.get(fruit);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void balance_updateAmount_ok() {
        Storage.data.put(new Fruit("apple"), 2);
        operationHandler.process(fruitTransaction);
        int expected = 10;
        int actual = Storage.data.get(fruit);
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.data.clear();
    }
}
