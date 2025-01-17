package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE = "test_inventory.txt";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeFile_emptyInventory_createsFileWithHeaderOnly() {
        Map<String, Integer> inventory = new HashMap<>();
        fileWriter.writeFile(TEST_FILE, inventory);
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "File should be created");
        try (BufferedReader br = new BufferedReader(new FileReader(TEST_FILE))) {
            String header = br.readLine();
            assertEquals("fruit,quantity", header, "Header should match");
            assertNull(br.readLine(), "No more lines should exist");
        } catch (IOException e) {
            fail("IOException should not be thrown during reading the file");
        } finally {
            file.delete();
        }
    }

    @Test
    void writeFile_nullFileName_throwsException() {
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("apple", 10);
        assertThrows(RuntimeException.class, () -> fileWriter.writeFile(null, inventory));
    }

    @Test
    void writeFile_nullInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> fileWriter.writeFile(TEST_FILE, null));
    }
}
