package core.basesyntax.store.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.store.Storage;
import core.basesyntax.store.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {

    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        Storage.clearStorage(); // Очищаємо склад перед кожним тестом
    }

    @Test
    void apply_shouldIncreaseFruitQuantityOnSupply() {
        // Ініціалізація фруктів на складі
        String fruit = "apple";
        Storage.modifyFruitStorage(fruit, 100); // Додаємо 100 яблук на склад

        // Створення транзакції постачання
        int supplyQuantity = 50;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, supplyQuantity);

        // Виконання операції постачання
        supplyOperation.apply(transaction);

        // Перевірка, що кількість яблук на складі збільшилась на 50
        assertEquals(150, Storage.getFruitQuantity(fruit),
                "The fruit quantity should increase by the supplied amount.");
    }

    @Test
    void apply_shouldNotChangeStorageIfSupplyQuantityIsZero() {
        // Ініціалізація фруктів на складі
        String fruit = "banana";
        Storage.modifyFruitStorage(fruit, 50); // Додаємо 50 бананів на склад

        // Створення транзакції постачання з нульовою кількістю
        int supplyQuantity = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, supplyQuantity);

        // Виконання операції постачання
        supplyOperation.apply(transaction);

        // Перевірка, що кількість бананів не змінилась
        assertEquals(50, Storage.getFruitQuantity(fruit),
                "The fruit quantity should remain unchanged if the supply quantity is zero.");
    }

    @Test
    void apply_shouldIncreaseFruitQuantityForMultipleSupplies() {
        // Ініціалізація фруктів на складі
        String fruit = "orange";
        Storage.modifyFruitStorage(fruit, 30); // Додаємо 30 апельсинів на склад

        // Створення двох транзакцій постачання
        int firstSupplyQuantity = 20;
        FruitTransaction firstTransaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, firstSupplyQuantity);

        int secondSupplyQuantity = 10;
        FruitTransaction secondTransaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, secondSupplyQuantity);

        // Виконання операцій постачання
        supplyOperation.apply(firstTransaction);
        supplyOperation.apply(secondTransaction);

        // Перевірка, що кількість апельсинів на складі збільшилась на 30
        assertEquals(60, Storage.getFruitQuantity(fruit),
                "The fruit quantity should increase by the total supply amount.");
    }
}
