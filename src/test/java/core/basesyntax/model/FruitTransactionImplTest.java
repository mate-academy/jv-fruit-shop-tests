package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionImplTest {
    @Test
    void setAndGetOperation_ShouldReturnCorrectOperation() {
        FruitTransactionImpl fruitTransaction = new FruitTransactionImpl();
        fruitTransaction.setOperation(FruitTransactionImpl.Operation.PURCHASE);

        assertEquals(FruitTransactionImpl.Operation.PURCHASE, fruitTransaction.getOperation());
    }

    @Test
    void setAndGetFruit_ShouldReturnCorrectFruit() {
        FruitTransactionImpl fruitTransaction = new FruitTransactionImpl();
        fruitTransaction.setFruit("apple");

        assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    void setAndGetQuantity_ShouldReturnCorrectQuantity() {
        FruitTransactionImpl fruitTransaction = new FruitTransactionImpl();
        fruitTransaction.setQuantity(10);

        assertEquals(10, fruitTransaction.getQuantity());
    }

    @Test
    void getOperationByCode_ShouldReturnCorrectOperation() {
        assertEquals(FruitTransactionImpl.Operation.BALANCE,
                FruitTransactionImpl.Operation.getOperationByCode("b"));
        assertEquals(FruitTransactionImpl.Operation.SUPPLY,
                FruitTransactionImpl.Operation.getOperationByCode("s"));
        assertEquals(FruitTransactionImpl.Operation.PURCHASE,
                FruitTransactionImpl.Operation.getOperationByCode("p"));
        assertEquals(FruitTransactionImpl.Operation.RETURN,
                FruitTransactionImpl.Operation.getOperationByCode("r"));
    }

    @Test
    void getOperationByCode_ShouldThrowExceptionForInvalidCode() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransactionImpl.Operation.getOperationByCode("x"));
    }
}
