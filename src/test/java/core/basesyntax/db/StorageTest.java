package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    void setUp() {
        // Очищаем хранилище перед каждым тестом
        Map<String, Integer> fruits = Storage.getAllFruits();
        fruits.keySet().forEach(fruit ->
                Storage.modifyFruitStorage(fruit, -Storage.getFruitQuantity(fruit)));
    }

    @Test
    void modifyFruitStorage_addPositiveQuantity_ok() {
        Storage.modifyFruitStorage("apple", 50);
        int actual = Storage.getFruitQuantity("apple");
        assertEquals(50, actual, "Количество яблок должно быть 50");
    }

    @Test
    void modifyFruitStorage_addZeroQuantity_ok() {
        Storage.modifyFruitStorage("apple", 0);
        int actual = Storage.getFruitQuantity("apple");
        assertEquals(0, actual, "Количество яблок должно быть 0");
    }

    @Test
    void modifyFruitStorage_addNegativeQuantity_ok() {
        Storage.modifyFruitStorage("apple", 50); // Добавляем 50
        Storage.modifyFruitStorage("apple", -20); // Уменьшаем на 20
        int actual = Storage.getFruitQuantity("apple");
        assertEquals(30, actual, "Количество яблок должно быть 30");
    }

    @Test
    void modifyFruitStorage_addNegativeQuantity_throwsException() {
        Storage.modifyFruitStorage("apple", 10); // Добавляем 10
        Exception exception = assertThrows(IllegalStateException.class,
                () -> Storage.modifyFruitStorage("apple", -20));
        String expectedMessage = "Stock for fruit apple cannot be negative.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getFruitQuantity_nonExistingFruit_returnsZero() {
        int actual = Storage.getFruitQuantity("banana");
        assertEquals(0, actual, "Количество для несуществующего фрукта должно быть 0");
    }

    @Test
    void getAllFruits_returnsAllEntries_ok() {
        Storage.modifyFruitStorage("apple", 30);
        Storage.modifyFruitStorage("banana", 20);
        Map<String, Integer> fruits = Storage.getAllFruits();

        assertEquals(2, fruits.size(), "Размер хранилища должен быть 2");
        assertEquals(30, fruits.get("apple"), "Количество яблок должно быть 30");
        assertEquals(20, fruits.get("banana"), "Количество бананов должно быть 20");
    }

    @Test
    void getAllFruits_doesNotAffectOriginalStorage_ok() {
        Storage.modifyFruitStorage("apple", 10);
        Map<String, Integer> fruits = Storage.getAllFruits();

        fruits.put("banana", 20); // Изменяем локальную копию
        assertFalse(Storage.getAllFruits().containsKey("banana"),
                "Оригинальное хранилище не должно содержать банан");
    }
}
