package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {

    private static final String FILE_PATH = "src/test/resources/test.csv";

    @Test
    void writeValidDataToFileIsOk() throws IOException {
        CsvFileWriter writer = new CsvFileWriter();
        String expected = "fruit,quantity banana,152 apple,90";
        writer.write(expected, FILE_PATH);
        String actual = Files.readString(Path.of(FILE_PATH));
        assertEquals(expected, actual, "Method should return: " + expected + "but was: " + actual);
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILE_PATH));
    }
}
