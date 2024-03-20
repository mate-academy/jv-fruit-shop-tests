package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitReaderTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validFile.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/invalidFile.csv";
    private static final String NULL_FILE_PATH = null;
    private static final String CORRECT_READ =
            "type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple,100\n"
                    + "s,banana,100\n"
                    + "p,banana,13\n";
    private static final String INCORRECT_READ =
            "type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple,100\n"
                    + "s,banana,100\n"
                    + "p,banana,13";

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
    public void dao_readDataFromFile_NotOk() {
        String actual = fruitDao.readFromFile(fileToRead);
        assertNotEquals(INCORRECT_READ, actual);
    }

    @Test
    public void dao_readInvalidFilePath_NotOk() {
        fileToRead = new File(INVALID_FILE_PATH);
        assertThrows(RuntimeException.class,
                () -> fruitDao.readFromFile(fileToRead));
    }

    @Test
    public void dao_readNullFilePath_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fruitDao.readFromFile(new File(NULL_FILE_PATH)));
    }
}
