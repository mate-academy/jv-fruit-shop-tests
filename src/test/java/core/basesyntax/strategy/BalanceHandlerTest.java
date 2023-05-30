package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {

    private static final int DEFAULT_QUANTITY = 15;
    private static final String DEFAULT_FRUIT = "banana";
    private FruitTransaction fruitTransaction;
    private OperationHandler balanceHandler;

    @BeforeEach
    void setUp() {
        Storage.dataBase.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceHandler = new BalanceHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.dataBase.clear();
    }

    @Test
    void balance_notNull_ok() {
        assertNotNull(Storage.dataBase.get(fruitTransaction.getFruit()),
                "Balance can't be null");
    }

    @Test
    void balance_equals_Ok() {
        int expectedQuantity = fruitTransaction.getQuantity();
        int actualQuantity = Storage.dataBase.get(fruitTransaction.getFruit());
        balanceHandler.operate(fruitTransaction);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void balance_NotEquals_Ok() {
        fruitTransaction.setQuantity(20);
        int expectedQuantity = fruitTransaction.getQuantity();
        int actualQuantity = Storage.dataBase.get(fruitTransaction.getFruit());
        balanceHandler.operate(fruitTransaction);
        assertNotEquals(expectedQuantity, actualQuantity);
    }
}
