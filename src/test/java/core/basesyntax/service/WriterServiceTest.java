package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String FILE_NAME_NOT_OK = "E://results_any.csv";
    private static final String FILE_NAME_OK = "results.csv";
    private static final String FRUIT_FOR_STORAGE = "banana";
    private static final Integer FRUIT_AMOUNT_STORAGE = 150;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String SECOND_LINE = "banana,150";
    private static WriterServiceImpl writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = FruitStoreException.class)
    public void writeFile_ErrorInFileName_NotOk() {
        Storage.storage.put(FRUIT_FOR_STORAGE, FRUIT_AMOUNT_STORAGE);
        writerService.writeReportToFile(FILE_NAME_NOT_OK, "");
    }

    @Test
    public void writeFile_Ok() {
        String message = FIRST_LINE + System.lineSeparator() + SECOND_LINE;
        Storage.storage.put(FRUIT_FOR_STORAGE, FRUIT_AMOUNT_STORAGE);
        writerService.writeReportToFile(FILE_NAME_OK, message);
        File file = new File(FILE_NAME_OK);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            assertEquals("Header line in file not correct " + line, line, FIRST_LINE);
            line = bufferedReader.readLine();
            assertEquals("First data in second line in file not correct", line, SECOND_LINE);
        } catch (IOException e) {
            throw new FruitStoreException("Can't read from file " + file);
        }
    }
}
