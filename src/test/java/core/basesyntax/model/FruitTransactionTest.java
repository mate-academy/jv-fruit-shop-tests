package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructor_shouldInitializeCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 100);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("Apple", transaction.getFruit());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void setOperation_shouldUpdateOperation() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Apple", 100);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        assertEquals(FruitTransaction.Operation.RETURN, transaction.getOperation());
    }

    @Test
    void setFruit_shouldUpdateFruitName() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "Apple", 100);
        transaction.setFruit("Banana");
        assertEquals("Banana", transaction.getFruit());
    }

    @Test
    void setQuantity_shouldUpdateQuantity() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "Apple", 100);
        transaction.setQuantity(200);
        assertEquals(200, transaction.getQuantity());
    }

    @Test
    void toString_shouldReturnProperStringRepresentation() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Orange", 50);
        assertEquals("FruitTransaction{operation=SUPPLY, fruit='Orange', quantity=50}",
                transaction.toString());
    }

    @Test
    void operation_shouldReturnCorrectCode() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode());
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode());
    }
}
