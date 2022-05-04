package operation;

import db.Storage;
import model.Fruit;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private final OperationHandler operationHandler;
    private final Fruit fruit;
    private final FruitTransaction fruitTransaction;

    private SupplyOperationHandlerTest() {
        this.operationHandler = new SupplyOperationHandler();
        this.fruit = new Fruit("apple");
        this.fruitTransaction = new FruitTransaction("b", fruit, 8);
    }

    @Test
    void supply_byCorrectAmount_ok() {
        operationHandler.process(fruitTransaction);
        int expected = 8;
        int actual = Storage.data.get(fruit);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void supply_updateAmount_ok() {
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
