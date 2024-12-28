package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        FruitStorage.fruits.clear();
    }

    @AfterEach
    void afterEach() {
        FruitStorage.fruits.clear();
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
}
