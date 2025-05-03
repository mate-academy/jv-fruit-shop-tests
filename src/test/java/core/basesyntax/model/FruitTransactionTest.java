package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
    }

    @Test
    void getOperation_ValidTransaction_ReturnsOperation() {
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    void setOperation_ValidOperation_OperationIsSet() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
    }

    @Test
    void getFruit_ValidTransaction_ReturnsFruit() {
        assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    void setFruit_ValidFruit_FruitIsSet() {
        fruitTransaction.setFruit("banana");
        assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_ValidTransaction_ReturnsQuantity() {
        assertEquals(10, fruitTransaction.getQuantity());
    }

    @Test
    void setQuantity_ValidQuantity_QuantityIsSet() {
        fruitTransaction.setQuantity(5);
        assertEquals(5, fruitTransaction.getQuantity());
    }

    @Test
    void enumValues_EnumValuesExist_EnumValuesCountMatches() {
        assertEquals(4, FruitTransaction.Operation.values().length);
    }

    @Test
    void getByCode_ExistingCode_ReturnsOperation() {
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.getByCode("s"));
    }

    @Test
    void getByCode_UnknownCode_RuntimeExceptionThrown() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.getByCode("x"));
    }
}
