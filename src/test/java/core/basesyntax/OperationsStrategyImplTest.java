package core.basesyntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class OperationsStrategyImplTest {
    private OperationsStrategyImpl operationsStrategy;
    private Fruits fruit;

    @BeforeEach
    void setUp() {
        fruit = new Fruits();
        operationsStrategy = new OperationsStrategyImpl(fruit);
    }

    @Test
    void testBalanceFunction() {
        int operation = operationsStrategy.operation("b", "banana", 300);
        assertEquals(300,operation);
    }

    @Test
    void testPurchaseFunction() {
        fruit.balance("banana",400);
        assertEquals(200,fruit.purchase("banana",200));
    }

    @Test
    void testSupplyFunction() {
        fruit.balance("apple",200);
        assertEquals(300,fruit.supply("apple",100));
    }

    @Test
    void testReturnFunction() {
        fruit.balance("orange",200);
        assertEquals(300,fruit.returnFruit("orange",100));
    }
}
