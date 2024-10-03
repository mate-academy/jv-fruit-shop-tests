package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String FRUIT = "apple";
    private static final Action ACTION = Action.SUPPLY;
    private static final int VALUE = 5;
    private static final String ALTER_FRUIT = "grape";
    private static final Action ALTER_ACTION = Action.BALANCE;
    private static final int ALTER_VALUE = 44;
    private static FruitTransaction exampleTransaction;

    @BeforeEach
    public void beforeEach() {
        exampleTransaction = new FruitTransaction(
                ACTION, FRUIT, VALUE);
    }

    @Test
    void fruitTransaction_getAction_Ok() {
        assertEquals(exampleTransaction.getAction(), ACTION);
    }

    @Test
    void fruitTransaction_setAction_Ok() {
        exampleTransaction.setAction(ALTER_ACTION);
        assertEquals(exampleTransaction.getAction(),ALTER_ACTION);
    }

    @Test
    void fruitTransaction_getFruit_Ok() {
        assertEquals(exampleTransaction.getFruit(), FRUIT);
    }

    @Test
    void fruitTransaction_setFruit() {
        exampleTransaction.setFruit(ALTER_FRUIT);
        assertEquals(exampleTransaction.getFruit(),ALTER_FRUIT);
    }

    @Test
    void fruitTransaction_getValue_Ok() {
        assertEquals(exampleTransaction.getValue(), VALUE);
    }

    @Test
    void fruitTransaction_setValue() {
        exampleTransaction.setValue(ALTER_VALUE);
        assertEquals(exampleTransaction.getValue(),ALTER_VALUE);
    }
}
