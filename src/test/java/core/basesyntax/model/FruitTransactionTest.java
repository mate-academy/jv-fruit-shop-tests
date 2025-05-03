package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {
    private static final String FRUIT_NAME = "someFruit";
    private static final int QUANTITY = 10;
    private static final String LETTER_FOR_BALANCE = "b";
    private static final String LETTER_FOR_SUPPLY = "s";
    private static final String LETTER_FOR_PURCHASE = "p";
    private static final String LETTER_FOR_RETURN = "r";

    @Test
    public void make_transactionWithBalanceOperation_ok() {
        FruitTransaction actual = new FruitTransaction(LETTER_FOR_BALANCE, FRUIT_NAME,
                    QUANTITY);
        assertEquals(FruitTransaction.Operation.BALANCE, actual.getOperation());
        assertEquals(FRUIT_NAME, actual.getFruit());
        assertEquals(QUANTITY, actual.getQuantity());
    }

    @Test
    public void make_transactionWithSupplyOperation_ok() {
        FruitTransaction actual = new FruitTransaction(LETTER_FOR_SUPPLY, FRUIT_NAME,
                QUANTITY);
        assertEquals(FruitTransaction.Operation.SUPPLY, actual.getOperation());
        assertEquals(FRUIT_NAME, actual.getFruit());
        assertEquals(QUANTITY, actual.getQuantity());
    }

    @Test
    public void make_transactionWithPurchaseOperation_ok() {
        FruitTransaction actual = new FruitTransaction(LETTER_FOR_PURCHASE, FRUIT_NAME,
                QUANTITY);
        assertEquals(FruitTransaction.Operation.PURCHASE, actual.getOperation());
        assertEquals(FRUIT_NAME, actual.getFruit());
        assertEquals(QUANTITY, actual.getQuantity());
    }

    @Test
    public void make_transactionWithReturnOperation_ok() {
        FruitTransaction actual = new FruitTransaction(LETTER_FOR_RETURN, FRUIT_NAME,
                QUANTITY);
        assertEquals(FruitTransaction.Operation.RETURN, actual.getOperation());
        assertEquals(FRUIT_NAME, actual.getFruit());
        assertEquals(QUANTITY, actual.getQuantity());
    }
}
