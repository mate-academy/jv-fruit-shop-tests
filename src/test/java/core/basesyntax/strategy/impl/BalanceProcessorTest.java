package core.basesyntax.strategy.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static core.basesyntax.strategy.FruitTransaction.Operation.BALANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.InvalidTransaction;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@DisplayName("BalanceProcessor Test")
class BalanceProcessorTest {
    private final OperationProcessor balanceProcessor = new BalanceProcessor(new ProductDaoImpl());

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @DisplayName("Check balance operation with valid value (Apple) and empty Storage")
    @Order(1)
    @Test
    void operate_checkBalanceAppleEmptyStorage_ok() {
        balanceProcessor.operate(new FruitTransaction(BALANCE, APPLE, 20));
        assertEquals(Storage.storage.get(APPLE), 20);
    }

    @DisplayName("Check balance operation with valid value (Apple) and non empty Storage")
    @Order(2)
    @Test
    void operate_checkBalanceApple_ok() {
        Storage.storage.put(APPLE, 10);
        balanceProcessor.operate(new FruitTransaction(BALANCE, APPLE, 20));
        assertEquals(Storage.storage.get(APPLE), 30);
    }

    @DisplayName("Check balance operation with valid value (Apple) and empty Storage")
    @Order(3)
    @Test
    void operate_checkBalanceBananaEmptyStorage_ok() {
        balanceProcessor.operate(new FruitTransaction(BALANCE, BANANA, 20));
        assertEquals(Storage.storage.get(BANANA), 20);
    }

    @DisplayName("Check balance operation with valid value (Apple) and non empty Storage")
    @Order(4)
    @Test
    void operate_checkBalanceBanana_ok() {
        Storage.storage.put(BANANA, 10);
        balanceProcessor.operate(new FruitTransaction(BALANCE, BANANA, 20));
        assertEquals(Storage.storage.get(BANANA), 30);
    }

    @DisplayName("Check balance operation with negative value")
    @Order(5)
    @Test
    void operate_checkBalanceNegative_notOk() {
        assertThrows(InvalidTransaction.class, () -> balanceProcessor.operate(
                new FruitTransaction(BALANCE, BANANA, -20)));
    }
}
