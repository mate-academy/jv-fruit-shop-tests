package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.strategy.handlers.BalanceOperation;
import core.basesyntax.strategy.handlers.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler operation;
    private FruitStorage storage;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        storage = new FruitStorage();
        operation = new BalanceOperation();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10);
    }

    @Test
    void handle_validInput_Ok() {
        operation.handle(fruitTransaction, storage);
        int expected = fruitTransaction.getQuantity();
        int actual = storage.getAmount("apple");
        assertEquals(expected, actual);
    }

    @Test
    void handle_fruitExist_Ok() {
        storage.saveItem("apple", 15);
        operation.handle(fruitTransaction, storage);
        int expected = 10;
        int actual = storage.getAmount("apple");
        assertEquals(expected, actual);
    }

    @Test
    void handle_negativeNumber_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -10);
        assertThrows(IllegalArgumentException.class,
                () -> operation.handle(fruitTransaction, storage));
    }

    @AfterEach
    void afterVoid() {
        storage.clear();
    }
}
