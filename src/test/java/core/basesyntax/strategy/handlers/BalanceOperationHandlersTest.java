package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlersTest {
    private BalanceOperationHandlers balanceOperationHandlers;

    @BeforeEach
    void setUp() {
        balanceOperationHandlers = new BalanceOperationHandlers();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void handle_fruitAdded_newQuantity_Ok() {
        String fruit = "apple";
        Integer quantity = 5;
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, quantity);
        balanceOperationHandlers.handle(transaction);
        assertEquals(quantity, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_existingFruitOverwritten_newQuantity_Ok() {
        String fruit = "apple";
        Integer initialQuantity = 5;
        FruitStorage.storage.put(fruit, initialQuantity);
        Integer newQuantity = 10;
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, newQuantity);
        balanceOperationHandlers.handle(transaction);
        assertEquals(newQuantity, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_newFruitAdded_newQuantity_Ok() {
        String fruit = "banana";
        Integer quantity = 3;
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, quantity);
        balanceOperationHandlers.handle(transaction);
        assertEquals(quantity, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_nullFruit_notOk() {
        String fruit = null;
        Integer quantity = 5;
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, quantity);
        assertThrows(RuntimeException.class, () -> {
            balanceOperationHandlers.handle(transaction);
        });
    }
}
