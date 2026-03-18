package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final FileWriter csvFileWriter = new CsvFileWriter();
    private static final String TEST_FILE_CONTENT = """
            fruit,quantity
            banana,152
            apple,90""";

    private static final String TEST_FILE_PATH = "src/test/resources/test-shop-report.csv";
    private static final String TEST_INVALID_FILE_PATH =
            "src/invalid/resources/test-shop-report.csv";

    @Test
    void write_validArguments_writesSameData_ok() {
        csvFileWriter.write(TEST_FILE_CONTENT, TEST_FILE_PATH);

        String result = null;
        try {
            result = Files.readString(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file, file path: " + TEST_FILE_PATH);
        }

        assertEquals(TEST_FILE_CONTENT, result, "write() does not write the same data");
    }

    @Test
    void write_dataIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                csvFileWriter.write(null, TEST_FILE_PATH));
    }

    @Test
    void write_filePathIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                csvFileWriter.write(TEST_FILE_CONTENT, null));
    }

    @Test
    void write_filePathIsInvalid() {
        assertThrows(RuntimeException.class, () ->
                csvFileWriter.write(TEST_FILE_CONTENT, TEST_INVALID_FILE_PATH));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }
}
