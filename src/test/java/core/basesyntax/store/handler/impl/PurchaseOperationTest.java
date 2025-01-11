package core.basesyntax.store.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.store.Storage;
import core.basesyntax.store.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        // Очищаємо Storage перед кожним тестом, щоб уникнути перехресних помилок
        Storage.clearStorage();
        Storage.modifyFruitStorage("apple", 100); // Ініціалізація з кількістю 100
        Storage.modifyFruitStorage("banana", 50);

        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void apply_shouldReduceFruitQuantityWhenPurchasing() {
        // Підготовка транзакції на покупку
        String fruit = "apple";
        int quantityToPurchase = 30;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, quantityToPurchase);

        // Виконуємо операцію покупки
        purchaseOperation.apply(transaction);

        // Перевіряємо, чи кількість фруктів була зменшена на вказану кількість
        int result = Storage.getFruitQuantity(fruit);
        assertEquals(70, result,
                "The fruit quantity should be reduced by the purchased amount.");
    }

    @Test
    void apply_shouldNotAllowNegativeQuantity() {
        // Підготовка транзакції на покупку більше, ніж є в наявності
        String fruit = "banana";
        int quantityToPurchase = 100; // Більше ніж 50, що є в наявності
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, quantityToPurchase);

        // Перевіряємо, що при виконанні операції виникає IllegalStateException
        assertThrows(IllegalStateException.class, () -> {
            purchaseOperation.apply(transaction);
        }, "The fruit quantity should not go below 0.");
    }

    @Test
    void apply_shouldHandleZeroPurchaseQuantity() {
        // Підготовка транзакції з кількістю 0
        String fruit = "apple";
        int quantityToPurchase = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, quantityToPurchase);

        // Виконуємо операцію покупки
        purchaseOperation.apply(transaction);

        // Перевіряємо, чи кількість фруктів не змінилась
        int result = Storage.getFruitQuantity(fruit);
        assertEquals(100, result,
                "The fruit quantity should remain the same if the purchase quantity is 0.");
    }
}

