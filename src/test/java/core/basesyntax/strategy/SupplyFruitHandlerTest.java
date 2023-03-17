package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyFruitHandlerTest {
    private static final String BANANA = "banana";
    private static final String OPERATION_BANANA = "s";
    private static final Integer VALID_VALUE = 10;
    private static final Integer BALANCE_VALUE = 50;
    private static final FruitTransaction TRANSACTION = new FruitTransaction(null, null, -1);
    private FruitHandler fruitHandler = new SupplyFruitHandler();

    @BeforeEach
    void setUp() {
        TRANSACTION.setOperation(null);
        TRANSACTION.setFruit(null);
        TRANSACTION.setValue(-1);
    }

    @Test
    void fruitTransactionValid_ok() {
        Storage.storage.put(BANANA, BALANCE_VALUE);
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        TRANSACTION.setFruit(BANANA);
        fruitHandler.apply(TRANSACTION);
        int expected = (int) VALID_VALUE + BALANCE_VALUE;
        int actual = Storage.storage.get(BANANA);
        assertEquals(expected, actual);
    }

    @Test
    void fruitInFruitTransactionNull_notOk() {
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        assertThrows(RuntimeException.class, () ->
                fruitHandler.apply(TRANSACTION));
    }

    @Test
    void operationInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        assertThrows(RuntimeException.class, () ->
                fruitHandler.apply(TRANSACTION));
    }

    @Test
    void valueInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setOperation(OPERATION_BANANA);
        assertThrows(RuntimeException.class, () ->
                fruitHandler.apply(TRANSACTION));
    }
}
