package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileWriterImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/java/resources/reportTest.csv";
    private static final String OUTPUT_FOLDER_PATH = "src/test/java/resources/";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT = "fruit,quantity" + LINE_SEPARATOR
            + "banana,10" + LINE_SEPARATOR
            + "apple,10";
    private static CsvFileWriter writer;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        writer = new CsvFileWriterImpl(OUTPUT_FILE_PATH);
    }

    @Test
    public void write_correctReport_ok() throws IOException {
        writer.write(REPORT);
        String report = String.join(LINE_SEPARATOR,
                Files.readAllLines(Path.of(OUTPUT_FILE_PATH)));
        assertEquals("Test failed! Expected report: " + REPORT + LINE_SEPARATOR
                + "But actual is: " + report, REPORT, report);
    }

    @Test
    public void write_emptyFile_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! Empty file was written, but expected RuntimeException");
        writer.write("");
    }

    @Test
    public void write_invalidFilePath_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! The file path is invalid '" + OUTPUT_FILE_PATH + '\''
                        + " Expected RuntimeException");
        writer = new CsvFileWriterImpl(OUTPUT_FOLDER_PATH);
        writer.write(REPORT);
    }
}
