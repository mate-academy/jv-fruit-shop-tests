package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    private static final String TEST_DATA_FILE = "src/test/java/resources/test.csv";

    @BeforeEach
    void resetStorage() {
        Storage.clearStorage();
    }

    @Test
    void addFruitsFromFile_ok() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                String fruit = data[0];
                int amount = Integer.parseInt(data[1]);
                Storage.add(fruit, amount);
            }
        }

        assertEquals(10, Storage.getAmount("apple"));
        assertEquals(5, Storage.getAmount("banana"));
        assertEquals(7, Storage.getAmount("orange"));
    }

    @Test
    void removeFruitsFromFile_ok() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                String fruit = data[0];
                int amount = Integer.parseInt(data[1]);
                Storage.add(fruit, amount);
            }
        }

        Storage.remove("apple", 5);
        Storage.remove("banana", 3);

        assertEquals(5, Storage.getAmount("apple"));
        assertEquals(2, Storage.getAmount("banana"));
        assertEquals(7, Storage.getAmount("orange"));
    }

    @Test
    void removeFruitNotEnough_notOk() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                String fruit = data[0];
                int amount = Integer.parseInt(data[1]);
                Storage.add(fruit, amount);
            }
        }

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Storage.remove("apple", 15);
        });

        assertEquals("Not enough apple in storage.", exception.getMessage());
    }
}
