package core.basesyntax.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/reader_input_test.txt";
    private static final String EMPTY_FILE_PATH = "";
    private static CsvFileReader reader;
    private String actual;
    private String expected;

    @Before
    public void setUp() {
        reader = new CsvFileReaderImpl();
    }

    @Test
    public void readFromFile_CorrectInputData_Ok() throws IOException {
        actual = reader.readFromFile(INPUT_FILE_PATH);
        expected = Files.readString(Path.of(INPUT_FILE_PATH));
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_IncorrectInputData_notOk() {
        reader.readFromFile(EMPTY_FILE_PATH);
    }
}
