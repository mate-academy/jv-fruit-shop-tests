package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReportWriterTest {
    private static final String OUTPUT_FILE = "src/test/resources/Output.csv";
    private static final String VALID_EXPECTED_STRING = """
            fruit,quantity
            potato,33
            apple,22
            cucumber,11
            """;
    private static DataWriter writer;

    static {
        writer = new CsvReportWriter();
    }

    @BeforeEach
    void createOutputFile() throws IOException {
        Files.createFile(Path.of(OUTPUT_FILE));
    }

    @AfterEach
    void deleteOutputFile() throws IOException {
        Files.delete(Path.of(OUTPUT_FILE));

    }

    @Test
    void write_allValidConditions_oK() throws IOException {
        writer.write(VALID_EXPECTED_STRING, OUTPUT_FILE);
        String actual = Files.readString(Path.of(OUTPUT_FILE));
        assertEquals(VALID_EXPECTED_STRING, actual);

    }

    @Test
    void write_nonExistentDestinationFile_oK() throws IOException {
        writer.write(VALID_EXPECTED_STRING,OUTPUT_FILE);
        String actual = Files.readString(Path.of(OUTPUT_FILE));
        assertEquals(VALID_EXPECTED_STRING, actual);
    }

    @Test
    void write_emptyStringInput_returnEmptyString() throws IOException {
        writer.write("", OUTPUT_FILE);
        String expected = "";
        String actual = Files.readString(Path.of(OUTPUT_FILE));
        assertEquals(expected, actual);
    }

    @Test
    void write_nullString_throwsException() {
        assertThrows(RuntimeException.class,
                () -> writer.write(null, OUTPUT_FILE));
    }
}
