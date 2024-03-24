package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitServiceImpl;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitWriterTest {
    private static final String READ_FILE_PATH = "src/test/resources/validFile.csv";
    private static final String WRITE_FILE_PATH = "src/test/resources/writeFile.csv";
    private static final String INVALID_WRITE_FILE_PATH = "src/test/resources/file.csv";
    private static final String EXPECT = "fruit,quantity\nbanana,107\napple,100\n";
    private FruitDao fruitDao;
    private FruitService fruitService;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitService = new FruitServiceImpl();

        File fileToRead = new File(READ_FILE_PATH);
        File fileToWrite = new File(WRITE_FILE_PATH);

        String data = fruitDao.readFromFile(fileToRead);
        fruitDao.writeToFile(fileToWrite, fruitService.generateReport(data));
    }

    @Test
    public void testWriteToFile_Ok() {
        String actual = fruitDao.readFromFile(new File(WRITE_FILE_PATH));
        assertEquals(EXPECT, actual);
    }

    @Test
    public void testWriteToFile_InvalidFilePath_NotOk() {
        File fileToRead = new File(READ_FILE_PATH);
        File invalidFileToWrite = new File(INVALID_WRITE_FILE_PATH);

        String data = fruitDao.readFromFile(fileToRead);

        assertThrows(RuntimeException.class,
                () -> fruitDao.writeToFile(invalidFileToWrite, fruitService.generateReport(data)),
                "Invalid file path");
    }
}
