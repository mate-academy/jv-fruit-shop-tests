package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 20;
    private static OperationHandler supplyOperation;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(QUANTITY);
    }

    @AfterEach
    void afterEach() {
        FruitStorage.fruits.clear();
        transaction.setFruit(FRUIT);
        transaction.setQuantity(QUANTITY);
    }

    @Test
    void executeOperation_firstSupply_Ok() {
        supplyOperation.executeOperation(transaction);
        int expected = 20;
        int actual = FruitStorage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void executeOperation_nextSupply_Ok() {
        supplyOperation.executeOperation(transaction);
        supplyOperation.executeOperation(transaction);
        int expected = 40;
        int actual = FruitStorage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void executeOperation_fruitIsNull_NotOk() {
        transaction.setFruit(null);
        assertThrows(RuntimeException.class, () -> supplyOperation.executeOperation(transaction));
    }

    @Test
    void executeOperation_negativeQuantity_NotOk() {
        transaction.setQuantity(-20);
        assertThrows(RuntimeException.class, () -> supplyOperation.executeOperation(transaction));
    }
}
