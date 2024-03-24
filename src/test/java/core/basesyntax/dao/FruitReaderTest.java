package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitReaderTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validFile.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/invalidFile.csv";
    private static final String NONE_EXISTENT_FILE_PATH = "nonexistentFile.csv";
    private static final String NULL_FILE_PATH = null;
    private static final String CORRECT_READ =
            "type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple,100\n"
                    + "s,banana,100\n"
                    + "p,banana,13\n";

    private FruitDao fruitDao;
    private File fileToRead;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        fileToRead = new File(VALID_FILE_PATH);
    }

    @Test
    public void dao_readDataFromFile_Ok() {
        String actual = fruitDao.readFromFile(fileToRead);
        assertEquals(CORRECT_READ, actual);
    }

    @Test
    public void dao_readInvalidFilePath_NotOk() {
        fileToRead = new File(INVALID_FILE_PATH);
        assertThrows(RuntimeException.class,
                () -> fruitDao.readFromFile(fileToRead),
                "Invalid file path");
    }

    @Test
    public void dao_readNonExistentFile_NotOk() {
        fileToRead = new File(NONE_EXISTENT_FILE_PATH);
        assertThrows(RuntimeException.class,
                () -> fruitDao.readFromFile(fileToRead),
                "File does not exist");
    }

    @Test
    public void dao_readNullFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fruitDao.readFromFile(new File(NULL_FILE_PATH)),
                "File can not be null");
    }
}
