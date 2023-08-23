package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private String code;
    private FruitTransaction balanceBanana;
    private int quantity;
    private String fruit;
    private FruitTransaction.Operation operation;

    @BeforeEach
    public void setUp() {
        balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 120);
        quantity = balanceBanana.getQuantity();
        fruit = balanceBanana.getFruit();
        operation = balanceBanana.getOperation();
        code = operation.getCode();
    }

    @Test
    void addAndGetOk() {
        assertEquals(quantity, 120);
        assertEquals(fruit, "banana");
        assertEquals(operation, FruitTransaction.Operation.BALANCE);
        assertEquals("b", code);
    }

    @Test
    void setOk() {
        FruitTransaction balanceBanana =
                new FruitTransaction();
        balanceBanana.setQuantity(220);
        balanceBanana.setFruit("apple");
        balanceBanana.setOperation(FruitTransaction.Operation.SUPPLY);
        int quantity = balanceBanana.getQuantity();
        String fruit = balanceBanana.getFruit();
        FruitTransaction.Operation operation = balanceBanana.getOperation();
        assertEquals(quantity, 220);
        assertEquals(fruit, "apple");
        assertEquals(operation, FruitTransaction.Operation.SUPPLY);
    }

    @Test
    void parseOperationOk() {
        FruitTransaction.Operation code = FruitTransaction.Operation.parseOperation("b");
        assertEquals(FruitTransaction.Operation.BALANCE, code);
    }

    @Test
    void constructorTwoParametersOk() {
        FruitTransaction balanceBanana =
                new FruitTransaction("banana", 120);
        int quantity = balanceBanana.getQuantity();
        String fruit = balanceBanana.getFruit();
        assertEquals(quantity, 120);
        assertEquals(fruit, "banana");
    }
}
