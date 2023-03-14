package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvFileReader;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileReaderImplTest {
    private static final String FILE_SOURCE = "src/test/java/resources/sourceTest.csv";
    private static final String EMPTY_FILE_SOURCE = "src/test/java/resources/emptySourceTest.csv";
    private static final String NON_EXISTING_FILE = "nonExistingFile";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final List<String> EXPECTED_RESULT = List.of(
            "Operation,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private CsvFileReader reader;

    @Test
    public void read_csvFileWithCorrectData_ok() {
        reader = new CsvFileReaderImpl(FILE_SOURCE);
        assertEquals("Test failed! Expected list with the following data: "
                        + EXPECTED_RESULT + LINE_SEPARATOR + "But actual is: " + reader.read(),
                EXPECTED_RESULT, reader.read());
    }

    @Test
    public void read_emptyFile_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage("Test failed! File '" + EMPTY_FILE_SOURCE
                + "' is empty, expected RuntimeException, but was thrown nothing");
        reader = new CsvFileReaderImpl(EMPTY_FILE_SOURCE);
        reader.read();
    }

    @Test
    public void read_fileNotFound_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! File doesn't exist. Expected RuntimeException");
        reader = new CsvFileReaderImpl(NON_EXISTING_FILE);
        reader.read();
    }
}
