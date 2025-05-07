package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class FruitOperationTest {
    @Test
    void testConstructorAndGetters() {
        FruitOperation.Operation operation = FruitOperation.Operation.SUPPLY;
        String fruit = "apple";
        int quantity = 50;

        FruitOperation fruitOp = new FruitOperation(operation, fruit, quantity);

        assertEquals(operation, fruitOp.getOperation());
        assertEquals(fruit, fruitOp.getFruit());
        assertEquals(quantity, fruitOp.getQuantity());
    }

    @Test
    void testSetters() {
        FruitOperation fruitOp = new FruitOperation();
        fruitOp.setOperation(FruitOperation.Operation.RETURN);
        fruitOp.setFruit("banana");
        fruitOp.setQuantity(30);

        assertEquals(FruitOperation.Operation.RETURN, fruitOp.getOperation());
        assertEquals("banana", fruitOp.getFruit());
        assertEquals(30, fruitOp.getQuantity());
    }

    @Test
    void testEqualsAndHashCode() {
        FruitOperation op1 = new FruitOperation(FruitOperation.Operation.BALANCE, "orange", 20);
        FruitOperation op2 = new FruitOperation(FruitOperation.Operation.BALANCE, "orange", 20);
        FruitOperation op3 = new FruitOperation(FruitOperation.Operation.SUPPLY, "orange", 20);

        assertEquals(op1, op2);
        assertEquals(op1.hashCode(), op2.hashCode());

        assertNotEquals(op1, op3);
        assertNotEquals(op1.hashCode(), op3.hashCode());
    }

    @Test
    void testToString() {
        FruitOperation op = new FruitOperation(FruitOperation.Operation.PURCHASE, "grape", 10);
        String expected = "FruitOperation{operation=PURCHASE, fruit='grape', quantity=10}";
        assertEquals(expected, op.toString());
    }

    @Test
    void testOperationGetOperationValid() {
        assertEquals(FruitOperation.Operation.BALANCE, FruitOperation.Operation.getOperation("b"));
        assertEquals(FruitOperation.Operation.SUPPLY, FruitOperation.Operation.getOperation("s"));
        assertEquals(FruitOperation.Operation.PURCHASE, FruitOperation.Operation.getOperation("p"));
        assertEquals(FruitOperation.Operation.RETURN, FruitOperation.Operation.getOperation("r"));
    }

    @Test
    void testOperationGetOperationInvalid() {
        assertNull(FruitOperation.Operation.getOperation("x"));
    }

    @Test
    void testOperationGetCode() {
        assertEquals("b", FruitOperation.Operation.BALANCE.getCode());
        assertEquals("s", FruitOperation.Operation.SUPPLY.getCode());
        assertEquals("p", FruitOperation.Operation.PURCHASE.getCode());
        assertEquals("r", FruitOperation.Operation.RETURN.getCode());
    }
}
