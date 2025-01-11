package core.basesyntax.store.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.store.Storage;
import core.basesyntax.store.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {

    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        Storage.clearStorage(); // Очищаємо склад перед кожним тестом
    }

    @Test
    void apply_shouldIncreaseFruitQuantityOnReturn() {
        // Ініціалізація фруктів на складі
        String fruit = "apple";
        Storage.modifyFruitStorage(fruit, 100); // Додаємо 100 яблук на склад

        // Створення транзакції повернення
        int returnQuantity = 50;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, returnQuantity);

        // Виконання операції повернення
        returnOperation.apply(transaction);

        // Перевірка, що кількість яблук на складі збільшилась на 50
        assertEquals(150, Storage.getFruitQuantity(fruit),
                "The fruit quantity should increase by the returned amount.");
    }

    @Test
    void apply_shouldNotChangeStorageIfReturnQuantityIsZero() {
        // Ініціалізація фруктів на складі
        String fruit = "banana";
        Storage.modifyFruitStorage(fruit, 50); // Додаємо 50 бананів на склад

        // Створення транзакції повернення з нульовою кількістю
        int returnQuantity = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, returnQuantity);

        // Виконання операції повернення
        returnOperation.apply(transaction);

        // Перевірка, що кількість бананів не змінилась
        assertEquals(50, Storage.getFruitQuantity(fruit),
                "The fruit quantity should remain unchanged if the return quantity is zero.");
    }

    @Test
    void apply_shouldIncreaseFruitQuantityForMultipleReturns() {
        // Ініціалізація фруктів на складі
        String fruit = "orange";
        Storage.modifyFruitStorage(fruit, 30); // Додаємо 30 апельсинів на склад

        // Створення двох транзакцій повернення
        int firstReturnQuantity = 20;
        FruitTransaction firstTransaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, firstReturnQuantity);

        int secondReturnQuantity = 10;
        FruitTransaction secondTransaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, secondReturnQuantity);

        // Виконання операцій повернення
        returnOperation.apply(firstTransaction);
        returnOperation.apply(secondTransaction);

        // Перевірка, що кількість апельсинів на складі збільшилась на 30
        assertEquals(60, Storage.getFruitQuantity(fruit),
                "The fruit quantity should increase by the total return amount.");
    }
}
