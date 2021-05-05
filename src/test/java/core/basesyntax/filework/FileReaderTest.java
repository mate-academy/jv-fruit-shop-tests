package core.basesyntax.filework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exceptions.ReadFromFileException;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String CORRECT_PATH_FOR_READ = "src/main/resources/fruit_shop.csv";
    private static final String EMPTY_FILE_FOR_READ = "src/test/resources/test_empty_read_file.csv";
    private static final String INCORRECT_PATH_FOR_READ = "src/main/test/resources/fruits_shop.csv";
    private static final String INPUT_DATA_FILE = "type,fruit,quantity\n"
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";
    private static FileReader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new CsvFileReaderImpl();
    }

    @Test
    public void fileReaderReadFromFileNotEmpty_Ok() {
        String[] lines = reader.read(CORRECT_PATH_FOR_READ);
        assertTrue(lines.length > 0);
    }

    @Test
    public void fileReaderReadFromFile_Ok() {
        String[] lines = reader.read(CORRECT_PATH_FOR_READ);
        assertEquals(lines, INPUT_DATA_FILE.split(System.lineSeparator()));
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
