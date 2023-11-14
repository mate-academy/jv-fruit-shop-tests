package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
    private static final String INVALID_FILE_PATH = "src/notexistdirectory/output.csv";
    private static final String DATA_TO_WRITE = "Some data to write";
    private final FileWriter fileWriter = new FileWriterImpl();

    @AfterEach
    void tearDown() {
        File file = new File(OUTPUT_FILE_PATH);
        file.delete();
    }

    @Test
    void writeToFile_writeData_isOk() {
        fileWriter.writeToFile(DATA_TO_WRITE, OUTPUT_FILE_PATH);
        File file = new File(OUTPUT_FILE_PATH);
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String value = reader.readLine();
            assertEquals(DATA_TO_WRITE, value);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file" + OUTPUT_FILE_PATH, e);
        }
    }

    @Test
    void writeToFile_writeNullData_isOk() {
        fileWriter.writeToFile("", OUTPUT_FILE_PATH);
        File file = new File(OUTPUT_FILE_PATH);
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String value = reader.readLine();
            assertNull(value);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file" + OUTPUT_FILE_PATH, e);
        }
    }

    @Test
    void writeToFile_invalidFilePath_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(DATA_TO_WRITE, INVALID_FILE_PATH));
    }
}
