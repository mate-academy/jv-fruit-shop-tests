package core.basesyntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationsStrategyImplTest {
    private OperationsStrategyImpl operationsStrategy;
    private Fruits fruit;

    @BeforeEach
    void setUp() {
        fruit = new Fruits();
        operationsStrategy = new OperationsStrategyImpl(fruit);
    }

    @Test
    void check_B_function() {
        int operation = operationsStrategy.operation("b", "banana", 300);
        assertEquals(300,operation);
    }

    @Test
    void check_P_function() {
        fruit.balance("banana",400);
        assertEquals(200,fruit.purchase("banana",200));
    }

    @Test
    void check_S_function() {
        fruit.balance("apple",200);
        assertEquals(300,fruit.supply("apple",100));
    }

    @Test
    void check_R_function() {
        fruit.balance("orange",200);
        assertEquals(300,fruit.returnFruit("orange",100));
    }
}