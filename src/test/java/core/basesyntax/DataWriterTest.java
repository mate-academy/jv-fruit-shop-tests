package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.DataWriter;
import core.basesyntax.service.impl.DataWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class DataWriterTest {
    private static final String EMPTY_REPORT = "";
    private static final String EXPECTED_CONTENT =
            """
            fruit,quantity
            banana,152
            apple,90
            """;
    private static final String FILE_STRING_PATH = "src/test/resources/report.csv";
    private static final Path FILE_PATH = Path.of(FILE_STRING_PATH);
    private static final DataWriter writer = new DataWriterImpl();

    @AfterEach
    void deleteTestData() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    void dataWriterTest_checkCorrectFile_ok() {
        writer.write(EXPECTED_CONTENT, FILE_STRING_PATH);
        assertTrue(Files.exists(FILE_PATH));
    }

    @Test
    void dataWriterTest_checkFileContent_ok() throws IOException {
        writer.write(EXPECTED_CONTENT, FILE_STRING_PATH);
        assertEquals(EXPECTED_CONTENT, Files.readString(FILE_PATH));
    }

    @Test
    void dataWriterTest_emptyString_notOk() {
        assertThrows(RuntimeException.class, () -> writer.write(EMPTY_REPORT, FILE_STRING_PATH));
    }
}
