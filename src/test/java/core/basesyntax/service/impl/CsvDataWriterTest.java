package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class CsvDataWriterTest {
    private static final String VALID_STRING = "Some VALID STrING";
    private static final String EMPTY_STRING = "";
    private static final String NONEXISTENT_DESTINATION_FILE =
                                "src/test/test-resources/output-files/nonexistent.txt";
    private static final String VALID_DESTINATION_FILE =
                                "src/test/test-resources/output-files/TestValidFile.txt";
    private final CsvDataWriter csvDataWriter = new CsvDataWriter();

    @Test
    public void writeToFile_allValidConditions_Ok() throws IOException {
        csvDataWriter.writeToFile(VALID_DESTINATION_FILE, VALID_STRING);
        String actual = Files.readString(Path.of(VALID_DESTINATION_FILE));
        assertEquals(VALID_STRING, actual);
    }

    @Test
    public void writeToFile_nonexistentDestinationFile_Ok() throws IOException {
        csvDataWriter.writeToFile(NONEXISTENT_DESTINATION_FILE, VALID_STRING);
        String actual = Files.readString(Path.of(VALID_DESTINATION_FILE));
        assertEquals(VALID_STRING, actual);
    }

    @Test
    public void writeToFile_emptyString_NotOk() {
        assertThrows(RuntimeException.class,
                () -> csvDataWriter.writeToFile(VALID_DESTINATION_FILE, EMPTY_STRING));
    }

    @Test
    public void writeToFile_nullString_NotOk() {
        assertThrows(RuntimeException.class,
                () -> csvDataWriter.writeToFile(VALID_DESTINATION_FILE, null));
    }
}
