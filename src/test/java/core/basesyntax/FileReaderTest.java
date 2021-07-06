package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.Test;

public class FileReaderTest {

    private static final String READER_FILE_PATH = "src/test/resources"
            + "/fileReaderResources/input_OK.csv";
    private static final String NOT_EXISTING_FILE_PATH = "src/test/resources"
            + "/fileReaderResources/nevermind.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    public void test_readingFromFile_OK() {
        String expected = "this is  input text for testing my own FileReader 34!@#$%^&*()";
        List<String> stringsFromFile = fileReader.readFromFile(READER_FILE_PATH);
        String actual = stringsFromFile.get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_readingFromFile_Not_OK() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(NOT_EXISTING_FILE_PATH));
    }
}
