package core.basesyntax;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.impl.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    static final String FRUIT = "banana";
    static final int QUANTITY = 100;
    static final String CODE = "s";
    private FruitTransaction actual;

    @BeforeEach
    public void beforeTest() {
        actual = new FruitTransaction(CODE,FRUIT,QUANTITY);
    }

    @Test
    public void fruitTransaction_argumentIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(null,FRUIT,QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,null,QUANTITY));
    }

    @Test
    public void fruitTransaction_invalidCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction("q",FRUIT,QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction("ss",FRUIT,QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction("s7",FRUIT,QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(" s",FRUIT,QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction("s ",FRUIT,QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(" ",FRUIT,QUANTITY));
    }

    @Test
    public void fruitTransaction_negativeNumber_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,FRUIT,-10));
    }

    @Test
    public void fruitTransaction_invalidFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE," ",QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,"77",QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,"!#",QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,"банан",QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE," banana",QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,"banana ",QUANTITY));
    }

    @Test
    public void fruitTransaction_getOperation_Ok() {
        assertEquals(SUPPLY, actual.getOperation());
    }

    @Test
    public void fruitTransaction_getFruit_Ok() {
        assertEquals(FRUIT, actual.getFruit());
    }

    @Test
    public void fruitTransaction_getQuantity_Ok() {
        assertEquals(QUANTITY, actual.getQuantity());
    }
}
