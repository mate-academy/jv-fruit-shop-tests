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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE = "test_report.txt";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeFile_validReport_createsFileWithContent() {
        String report = "fruit,quantity\napple,10\nbanana,20";
        fileWriter.writeFile(TEST_FILE, report);

        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "File should be created");

        try (BufferedReader br = new BufferedReader(new FileReader(TEST_FILE))) {
            String firstLine = br.readLine();
            assertEquals("fruit,quantity", firstLine, "Header should match");

            String secondLine = br.readLine();
            assertEquals("apple,10", secondLine, "First data line should match");

            String thirdLine = br.readLine();
            assertEquals("banana,20", thirdLine, "Second data line should match");

            assertNull(br.readLine(), "No more lines should exist");
        } catch (IOException e) {
            fail("IOException should not be thrown during reading the file");
        } finally {
            file.delete();
        }
    }

    @Test
    void writeFile_emptyReport_createsFileWithNoContent() {
        String report = "";
        fileWriter.writeFile(TEST_FILE, report);

        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "File should be created");

        try (BufferedReader br = new BufferedReader(new FileReader(TEST_FILE))) {
            assertNull(br.readLine(), "No lines should exist in the file");
        } catch (IOException e) {
            fail("IOException should not be thrown during reading the file");
        } finally {
            file.delete();
        }
    }

    @Test
    void writeFile_nullFileName_throwsException() {
        String report = "fruit,quantity\napple,10";
        assertThrows(RuntimeException.class, () -> fileWriter.writeFile(null, report));
    }
}
