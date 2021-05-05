package core.basesyntax.filework;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exceptions.ReadFromFileException;
import org.junit.Before;
import org.junit.Test;

public class FileReaderTest {
    private static final String CORRECT_PATH_FOR_READ = "src/main/resources/fruit_shop.csv";
    private static final String EMPTY_FILE_FOR_READ = "src/test/resources/test_empty_read_file.csv";
    private static final String INCORRECT_PATH_FOR_READ = "src/main/test/resources/fruits_shop.csv";
    private static FileReader reader;

    @Before
    public void setUp() {
        reader = new CsvFileReaderImpl();
    }

    @Test
    public void fileReaderReadFromFileNotEmpty_Ok() {
        String[] lines = reader.read(CORRECT_PATH_FOR_READ);
        assertTrue(lines.length > 0);
    }

    @Test(expected = ReadFromFileException.class)
    public void fileReaderReadFromEmptyFile_NotOk() {
        reader.read(EMPTY_FILE_FOR_READ);
    }

    @Test(expected = ReadFromFileException.class)
    public void fileReaderReadFromIncorrectFile_NotOk() {
        reader.read(INCORRECT_PATH_FOR_READ);
    }
}
