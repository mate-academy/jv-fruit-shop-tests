package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 10;
    private static final int BANANA_QUANTITY = 5;

    @Test
    void testConstructorAndGetters_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, APPLE_QUANTITY);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals(APPLE, transaction.getFruit());
        assertEquals(APPLE_QUANTITY, transaction.getQuantity());
    }

    @Test
    void testSetters_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, BANANA_QUANTITY);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit(APPLE);
        transaction.setQuantity(APPLE_QUANTITY);

        assertEquals(FruitTransaction.Operation.RETURN, transaction.getOperation());
        assertEquals(APPLE, transaction.getFruit());
        assertEquals(APPLE_QUANTITY, transaction.getQuantity());
    }

    @Test
    void testEqualsAndHashCode_ok() {
        FruitTransaction transaction1
                = new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, APPLE, APPLE_QUANTITY);
        FruitTransaction transaction2
                = new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, APPLE, APPLE_QUANTITY);
        FruitTransaction transaction3
                = new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, BANANA, BANANA_QUANTITY);

        assertEquals(transaction1, transaction2);
        assertNotEquals(transaction1, transaction3);
        assertNotEquals(transaction2, transaction3);

        assertEquals(transaction1.hashCode(), transaction2.hashCode());
        assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

    @Test
    void testOperationEnum_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void testOperationEnumGetCode_ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode());
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode());
    }

}
