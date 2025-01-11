package core.basesyntax.store.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.store.Storage;
import core.basesyntax.store.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        // Очищаємо Storage перед кожним тестом, щоб уникнути перехресних помилок
        Storage.clearStorage();
        Storage.modifyFruitStorage("apple", 0); // Ініціалізація з кількістю 0
        Storage.modifyFruitStorage("banana", 0);

        balanceOperation = new BalanceOperation();
    }

    @Test
    void apply_shouldModifyStorageForBalanceOperation() {
        // Підготовка транзакції
        String fruit = "apple";
        int quantity = 100;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, fruit, quantity);

        // Виконуємо операцію
        balanceOperation.apply(transaction);

        // Перевіряємо, чи змінилася кількість фруктів у Storage
        int result = Storage.getFruitQuantity(fruit);
        assertEquals(quantity, result, "The fruit quantity should be updated correctly.");
    }

    @Test
    void apply_shouldHandleZeroQuantity() {
        // Підготовка транзакції з кількістю 0
        String fruit = "banana";
        int quantity = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, fruit, quantity);

        // Виконуємо операцію
        balanceOperation.apply(transaction);

        // Перевіряємо, чи кількість фруктів залишилась нульовою
        int result = Storage.getFruitQuantity(fruit);
        assertEquals(quantity, result, "The fruit quantity should remain 0.");
    }
}
