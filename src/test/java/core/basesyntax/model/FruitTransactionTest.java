package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction sameFruitTransaction;
    private static FruitTransaction differentFruitTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        sameFruitTransaction = new FruitTransaction();
        differentFruitTransaction = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction
                .Operation.getCode("s"));
        fruitTransaction.setAmount(100);

        sameFruitTransaction.setFruit("apple");
        sameFruitTransaction.setOperation(FruitTransaction
                .Operation.getCode("s"));
        sameFruitTransaction.setAmount(100);

        differentFruitTransaction.setFruit("orange");
        differentFruitTransaction.setOperation(FruitTransaction
                .Operation.getCode("b"));
        differentFruitTransaction.setAmount(40);
    }

    @Test
    void getCode_incorrectEnumOperation_NotOk() {
        String incorrectOperation = "incorrectOperation";
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.getCode(incorrectOperation));
    }

    @Test
    void getCode_BalanceEnumOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction
                .Operation.getCode("b");
        assertEquals(expected, actual);
    }

    @Test
    void getCode_SupplyEnumOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = FruitTransaction
                .Operation.getCode("s");
        assertEquals(expected,actual);
    }

    @Test
    void getCode_PurchaseEnumOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = FruitTransaction
                .Operation.getCode("p");
        assertEquals(expected,actual);
    }

    @Test
    void getCode_ReturnEnumOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitTransaction
                .Operation.getCode("r");
        assertEquals(expected,actual);
    }

    @Test
    void checkTheSameFruitsForEquals_Ok() {
        boolean actual = fruitTransaction
                .equals(sameFruitTransaction);
        assertTrue(actual);
    }

    @Test
    void checkDifferentFruitsForEquals_NotOk() {
        boolean actual = fruitTransaction
                .equals(differentFruitTransaction);
        assertFalse(actual);
    }

    @Test
    void checkTheSameFruitsForHashCode_Ok() {
        int fruitTransactionHashCode = fruitTransaction.hashCode();
        int sameFruitTransactionHashCode = sameFruitTransaction.hashCode();
        assertEquals(fruitTransactionHashCode,sameFruitTransactionHashCode);
    }

    @Test
    void checkDifferenceFruitsForHashCode_NotOk() {
        int fruitTransactionHashCode = fruitTransaction.hashCode();
        int differentFruitTransactionHashCode = differentFruitTransaction.hashCode();
        assertNotEquals(fruitTransactionHashCode,differentFruitTransactionHashCode);
    }
}
