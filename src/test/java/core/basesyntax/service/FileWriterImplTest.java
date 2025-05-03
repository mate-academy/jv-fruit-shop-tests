package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final Path ABSENT_FILE = Paths.get("src/test/resour/ces/absent.csv");
    private static final Path OUTPUT_FILE = Paths.get("src/test/resources/output.csv");
    private static final Path NEW_OUTPUT_FILE = Paths.get("src/test/resources/new_output.csv");
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_existingOutputFile_Ok() {
        String report = "fruit,quantity" + System.lineSeparator() + "banana,20";
        fileWriter.write(report, OUTPUT_FILE);
        String expected = "banana,20";
        String actual = readBackFile();
        assertEquals(expected, actual);
    }

    @Test
    void write_newOutputFile_Ok() {
        String report = "fruit,quantity" + System.lineSeparator() + "banana,20";
        fileWriter.write(report, NEW_OUTPUT_FILE);
        String expected = "banana,20";
        String actual = readBackFile();
        assertEquals(expected, actual);
    }

    @Test
    void write_absentOutputFile_NotOk() {
        String report = "fruit,quantity" + System.lineSeparator() + "banana,20";
        assertThrows(RuntimeException.class, () -> fileWriter.write(report, ABSENT_FILE));
    }

    @AfterAll
    static void deleteNewOutputFile() {
        try {
            Files.deleteIfExists(NEW_OUTPUT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can't delete file: '%s'.",
                    NEW_OUTPUT_FILE), e);
        }
    }

    private String readBackFile() {
        try {
            return Files.readAllLines(FileWriterImplTest.OUTPUT_FILE).get(1);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + OUTPUT_FILE, e);
        }
    }
}
