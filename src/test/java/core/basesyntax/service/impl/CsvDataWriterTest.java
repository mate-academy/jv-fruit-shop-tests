package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvDataWriterTest {
    private static final String VALID_STRING = "Some VALID STrING";
    private static final String EMPTY_STRING = "";
    private static final String NONEXISTENT_DESTINATION_FILE =
                                "src/test/test-resources/output-files/nonexistent.csv";
    private static final String VALID_DESTINATION_FILE =
                                "src/test/test-resources/output-files/TestValidFile.csv";
    private static CsvDataWriter csvDataWriter;
    @BeforeAll
     static void setUp() {
        csvDataWriter = new CsvDataWriter();
    }
    @Test
     void writeToFile_allValidConditions_Ok() throws IOException {
        csvDataWriter.writeToFile(VALID_DESTINATION_FILE, VALID_STRING);
        String actual = Files.readString(Path.of(VALID_DESTINATION_FILE));
        assertEquals(VALID_STRING, actual);
    }

    @Test
     void writeToFile_nonexistentDestinationFile_Ok() throws IOException {
        csvDataWriter.writeToFile(NONEXISTENT_DESTINATION_FILE, VALID_STRING);
        String actual = Files.readString(Path.of(VALID_DESTINATION_FILE));
        assertEquals(VALID_STRING, actual);
    }

    @Test
    void writeToFile_emptyString_NotOk() {
        assertThrows(RuntimeException.class,
                () -> csvDataWriter.writeToFile(VALID_DESTINATION_FILE, EMPTY_STRING));
    }

    @Test
    void writeToFile_nullString_NotOk() {
        assertThrows(RuntimeException.class,
                () -> csvDataWriter.writeToFile(VALID_DESTINATION_FILE, null));
    }
}
