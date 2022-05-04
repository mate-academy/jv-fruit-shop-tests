package operation;

import db.Storage;
import model.Fruit;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private final OperationHandler operationHandler;
    private final Fruit fruit;
    private final FruitTransaction fruitTransaction;

    private ReturnOperationHandlerTest() {
        this.operationHandler = new ReturnOperationHandler();
        this.fruit = new Fruit("apple");
        this.fruitTransaction = new FruitTransaction("b", fruit, 8);
    }

    @Test
    void return_byCorrectAmount_ok() {
        operationHandler.process(fruitTransaction);
        int expected = 8;
        int actual = Storage.data.get(fruit);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void return_updateAmount_ok() {
        Storage.data.put(fruit, 2);
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
