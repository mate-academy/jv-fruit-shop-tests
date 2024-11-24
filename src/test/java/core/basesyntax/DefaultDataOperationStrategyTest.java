package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultDataOperationStrategyTest {

    private FruitDB fruitDB;
    private DefaultDataOperationStrategy strategy;

    @BeforeEach
    void setUp() {
        fruitDB = new FruitDB();
        strategy = new DefaultDataOperationStrategy(fruitDB);
    }

    @Test
    void execute_balanceOperation_updatesInventory() {
        FruitTransaction transaction = new FruitTransaction("b", "apple", 50);

        strategy.execute(transaction, fruitDB);

        assertEquals(50, fruitDB.getInventory().get("apple"));
    }

    @Test
    void execute_supplyOperation_addsToInventory() {
        FruitTransaction transaction = new FruitTransaction("s", "banana", 30);

        strategy.execute(transaction, fruitDB);

        assertEquals(30, fruitDB.getInventory().get("banana"));
    }

    @Test
    void execute_purchaseOperation_reducesInventory() {
        fruitDB.add("orange", 40);
        FruitTransaction transaction = new FruitTransaction("p", "orange", 20);

        strategy.execute(transaction, fruitDB);

        assertEquals(20, fruitDB.getInventory().get("orange"));
    }

    @Test
    void execute_purchaseOperation_insufficientInventory_throwsException() {
        fruitDB.add("grape", 10);
        FruitTransaction transaction = new FruitTransaction("p", "grape", 20);

        assertThrows(IllegalArgumentException.class, () -> strategy.execute(transaction, fruitDB));
    }

    @Test
    void execute_returnOperation_increasesInventory() {
        fruitDB.add("peach", 15);
        FruitTransaction transaction = new FruitTransaction("r", "peach", 10);

        strategy.execute(transaction, fruitDB);

        assertEquals(25, fruitDB.getInventory().get("peach"));
    }

    @Test
    void execute_invalidOperation_throwsException() {
        FruitTransaction transaction = new FruitTransaction("x", "apple", 10);

        assertThrows(IllegalArgumentException.class, () -> strategy.execute(transaction, fruitDB));
    }

    @Test
    void execute_negativeQuantity_throwsException() {
        FruitTransaction transaction = new FruitTransaction("s", "apple", -10);

        assertThrows(IllegalArgumentException.class, () -> strategy.execute(transaction, fruitDB));
    }
}
