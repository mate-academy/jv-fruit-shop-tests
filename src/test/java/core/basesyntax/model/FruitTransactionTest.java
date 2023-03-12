package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION = BALANCE;
    private static final String DEFAULT_FRUIT = "apple";
    private static final int DEFAULT_QUANTITY = 50;
    private static FruitTransaction defaultTransaction;

    @BeforeClass
    public static void setUp() {
        defaultTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_FRUIT, DEFAULT_QUANTITY
        );
    }

    @Test
    public void getOperation_ok() {
        assertEquals(defaultTransaction.getOperation(), BALANCE);
    }

    @Test
    public void getFruit_ok() {
        assertEquals(defaultTransaction.getFruit(), DEFAULT_FRUIT);
    }

    @Test
    public void getQuantity_ok() {
        assertEquals(defaultTransaction.getQuantity(), DEFAULT_QUANTITY);
    }
}
