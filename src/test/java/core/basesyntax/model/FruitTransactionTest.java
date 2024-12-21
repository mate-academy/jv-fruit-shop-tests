package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void testFruitTransactionConstructorAndGetters() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        String fruit = "Apple";
        int quantity = 10;

        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);

        assertEquals(operation, transaction.getOperation());
        assertEquals(fruit, transaction.getFruit());
        assertEquals(quantity, transaction.getQuantity());
    }

    @Test
    void testSetters() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "Banana", 5);

        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("Orange");
        transaction.setQuantity(15);

        assertEquals(FruitTransaction.Operation.RETURN, transaction.getOperation());
        assertEquals("Orange", transaction.getFruit());
        assertEquals(15, transaction.getQuantity());
    }

    @Test
    void testOperationFromCodeValid() {
        String code = "s";

        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode(code);

        assertEquals(FruitTransaction.Operation.SUPPLY, operation);
    }

    @Test
    void testOperationFromCodeInvalid() {
        String code = "x";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode(code);
        });

        assertEquals("No enum constant for code: x", exception.getMessage());
    }
}
