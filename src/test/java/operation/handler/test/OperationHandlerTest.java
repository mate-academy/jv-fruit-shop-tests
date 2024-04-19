package operation.handler.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.basesyntax.strategy.impl.SupplyHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private OperationHandler returnHandler;
    private OperationHandler supplyHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        returnHandler = new ReturnHandlerImpl();
        supplyHandler = new SupplyHandlerImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20); // Установка количества для операций
        Storage.fruits.put("banana", 0); // Инициализация хранилища
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void apply_returnOperation_updatesStorageCorrectly() {
        returnHandler.apply(fruitTransaction);
        int expected = 20;
        int actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void apply_supplyOperation_updatesStorageCorrectly() {
        supplyHandler.apply(fruitTransaction);
        int expected = 20;
        int actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }
}
