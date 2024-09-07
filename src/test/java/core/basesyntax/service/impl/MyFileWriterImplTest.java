package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyFileWriterImplTest {
    private static final String DEFAULT_FILE_NAME = "finalReport.csv";
    private static final MyFileWriterImpl myFileWriter;

    static {
        myFileWriter = new MyFileWriterImpl();
    }

    @Test
    void parse_validInput_ok() {
        String expected = new String("test");
        myFileWriter.write(expected, DEFAULT_FILE_NAME);
        File file = new File(DEFAULT_FILE_NAME);
        assertTrue(file.exists(), "File should be created");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertEquals(expected, line, "File content should match the written string");
        } catch (IOException e) {
            Assertions.fail("An exception occurred while reading the file: " + e.getMessage());
        }
    }

    @Test
    void write_invalidInput_notOk() {
        String expected = "Text is null";
        RuntimeException actual = assertThrows(RuntimeException.class,
                () -> myFileWriter.write(null,"fakeFileToRead.csv"));
        assertEquals(expected, actual.getMessage());
    }
}
