package core.basesyntax.servises;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.file.reader.Reader;
import core.basesyntax.file.reader.ReaderImpl;
import core.basesyntax.file.writer.FileWriterImpl;
import core.basesyntax.file.writer.FileWriterInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String FINAL_REPORT_CSV = "src/test/resources/finalReport.csv";
    private static final String TEST_STRING = "Hello World!";
    private static final String EMPTY_STRING = "";
    private FileWriterInterface writer;
    private Reader reader;

    @BeforeEach
    void setUp() {
        writer = new FileWriterImpl();
        reader = new ReaderImpl();
    }

    @Test
    void validOutput_Ok() {
        writer.write(TEST_STRING, FINAL_REPORT_CSV);
        assertEquals(TEST_STRING, reader.read(FINAL_REPORT_CSV).get(0));
    }

    @Test
    void nullArguments_NoOk() {
        assertThrows(NullPointerException.class,
                () -> writer.write(null, FINAL_REPORT_CSV));
        assertThrows(NullPointerException.class,
                () -> writer.write(TEST_STRING,null));
    }

    @Test
    void emptyString_Ok() {
        writer.write(EMPTY_STRING, FINAL_REPORT_CSV);
        assertEquals(EMPTY_STRING, reader.read(FINAL_REPORT_CSV).get(0));
    }
}
