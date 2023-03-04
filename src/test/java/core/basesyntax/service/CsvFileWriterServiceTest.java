package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.Storage;
import core.basesyntax.exeption.FruitShopExeption;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static final String READ_FILE_NAME_NOTOK = "T://source_notok.csv";
    private static final String READ_FILE_NAME_OK = "report.csv";
    private static final String FRUIT_FOR_STORAGE = "banana";
    private static final Integer FRUIT_AMOUNT_STORAGE = 50;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String SECOND_LINE = "banana,50";
    private static CsvFileWriterService csvFileWriterServiceService;

    @BeforeClass
    public static void beforeClass() {
        csvFileWriterServiceService = new CsvFileWriterService();
    }

    @Test(expected = FruitShopExeption.class)
    public void readFile_NotOk() {
        Storage.fruits.put(FRUIT_FOR_STORAGE, FRUIT_AMOUNT_STORAGE);
        csvFileWriterServiceService.writeFile(READ_FILE_NAME_NOTOK, "");
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test
    public void readFile_Ok() {
        String message = FIRST_LINE + System.lineSeparator() + SECOND_LINE;
        Storage.fruits.put(FRUIT_FOR_STORAGE, FRUIT_AMOUNT_STORAGE);
        csvFileWriterServiceService.writeFile(READ_FILE_NAME_OK, message);

        File file = new File(READ_FILE_NAME_OK);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            assertEquals("Header line in file not correct " + line, line, FIRST_LINE);

            line = bufferedReader.readLine();
            assertEquals("First data in second line in file not correct", line, SECOND_LINE);

        } catch (IOException e) {
            throw new FruitShopExeption("Can't read from file " + file);
        }
    }
}

