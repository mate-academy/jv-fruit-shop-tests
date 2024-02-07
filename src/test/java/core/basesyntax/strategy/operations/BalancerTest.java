package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalancerTest {
    private static OperationCompiler balancer;

    @BeforeAll
    static void beforeAll() {
        balancer = new Balancer();
    }

    @Test
    void doOperation_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> balancer.doOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana", -1)));
    }

    @Test
    void doOperation_nullFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> balancer.doOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 10)));
    }

    @Test
    void doOperation_invalidOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> balancer.doOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)));
        assertThrows(IllegalArgumentException.class, () -> balancer.doOperation(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10)));
        assertThrows(IllegalArgumentException.class, () -> balancer.doOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10)));
    }

    @Test
    void doOperation_rightData_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        balancer.doOperation(fruitTransaction);
        assertEquals(fruitTransaction.getQuantity(),
                Storage.fruits.get(fruitTransaction.getFruit()));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
