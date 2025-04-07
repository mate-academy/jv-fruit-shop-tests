package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    private static final String TEST_DATA_FILE = "src/test/resources/test.csv";

    @BeforeEach
    void resetStorage() {
        Storage.clearStorage();
    }

    @Test
    void addFruitsFromFile_ok() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_DATA_FILE))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String type = data[0];
                String fruit = data[1];
                int amount = Integer.parseInt(data[2]);

                if (type.equals("b") || type.equals("s") || type.equals("r")) {
                    Storage.add(fruit, amount);
                } else if (type.equals("p")) {
                    Storage.remove(fruit, amount);
                }
            }
        }

        assertEquals(90, Storage.getAmount("apple"));
        assertEquals(152, Storage.getAmount("banana"));
    }

    @Test
    void removeFruits_ok() throws IOException {
        addFruitsFromFile_ok();

        // Check amounts before removal
        assertEquals(90, Storage.getAmount("apple"));
        assertEquals(152, Storage.getAmount("banana"));

        Storage.remove("apple", 50);
        Storage.remove("banana", 30);

        // Check amounts after removal
        assertEquals(40, Storage.getAmount("apple"));
        assertEquals(122, Storage.getAmount("banana"));
    }

    @Test
    void removeFruitNotEnough_notOk() throws IOException {
        addFruitsFromFile_ok();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Storage.remove("apple", 150);
        });

        assertEquals("Not enough apple in storage.", exception.getMessage());
    }
}
