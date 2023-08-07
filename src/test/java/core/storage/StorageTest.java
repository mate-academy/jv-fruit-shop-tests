package core.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    public void setUp() {
        Storage.fruitTransactions.clear();
    }

    @Test
    public void testFruitTransactions_AddTransaction() {
        String fruit = "Apple";
        int amount = 50;

        Storage.fruitTransactions.put(fruit, amount);

        assertTrue(Storage.fruitTransactions.containsKey(fruit),
                "Fruit transaction should be stored");
        assertEquals(amount, Storage.fruitTransactions.get(fruit),
                "Stored transaction amount should be correct");
    }

    @Test
    public void testFruitTransactions_AddMultipleTransactions() {
        String fruit1 = "Apple";
        int amount1 = 50;

        String fruit2 = "Banana";
        int amount2 = 100;

        Storage.fruitTransactions.put(fruit1, amount1);
        Storage.fruitTransactions.put(fruit2, amount2);

        assertTrue(Storage.fruitTransactions.containsKey(fruit1),
                "Fruit1 transaction should be stored");
        assertEquals(amount1, Storage.fruitTransactions.get(fruit1),
                "Stored transaction amount for fruit1 should be correct");

        assertTrue(Storage.fruitTransactions.containsKey(fruit2),
                "Fruit2 transaction should be stored");
        assertEquals(amount2, Storage.fruitTransactions.get(fruit2),
                "Stored transaction amount for fruit2 should be correct");
    }

    @Test
    public void testFruitTransactions_RemoveTransaction() {
        String fruit = "Apple";
        int amount = 50;
        Storage.fruitTransactions.put(fruit, amount);

        Storage.fruitTransactions.remove(fruit);

        assertFalse(Storage.fruitTransactions.containsKey(fruit),
                "Fruit transaction should be removed");
    }

    @Test
    public void testFruitTransactions_GetTransactionAmount() {
        String fruit = "Apple";
        int amount = 50;
        Storage.fruitTransactions.put(fruit, amount);

        int retrievedAmount = Storage.fruitTransactions.getOrDefault(fruit, 0);

        assertEquals(amount, retrievedAmount, "Retrieved transaction amount should be correct");
    }
}
