package core.basesyntax.servises;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.file.writer.FileWriterImpl;
import core.basesyntax.file.writer.FileWriterInterface;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String FINAL_REPORT_CSV = "src/test/resources/finalReport.csv";
    private static final String TEST_STRING = "Hello World!";
    private static final String EMPTY_STRING = "";
    private FileWriterInterface writer;

    @BeforeEach
    void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void write_validOutput_Ok() throws IOException {
        writer.write(TEST_STRING, FINAL_REPORT_CSV);
        String actual = Files.readString(Path.of(FINAL_REPORT_CSV));
        assertEquals(TEST_STRING + System.lineSeparator(), actual);
    }

    @Test
    void write_nullArguments_NoOk() {
        assertThrows(NullPointerException.class,
                () -> writer.write(null, FINAL_REPORT_CSV));
        assertThrows(NullPointerException.class,
                () -> writer.write(TEST_STRING,null));
    }

    @Test
    void write_emptyString_Ok() throws IOException {
        writer.write(EMPTY_STRING, FINAL_REPORT_CSV);
        String actual = Files.readString(Path.of(FINAL_REPORT_CSV));
        assertEquals(EMPTY_STRING + System.lineSeparator(), actual);
    }
}
