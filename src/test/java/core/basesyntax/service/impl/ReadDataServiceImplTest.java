package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReadDataService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadDataServiceImplTest {
    private static final String FILE_DATE_NAME = "src/main/java/resources/FruitShopTest.csv";
    private static ReadDataService readDataService;

    @BeforeClass
    public static void beforeClass() {
        readDataService = new ReadDataServiceImpl();
        String fruitShopDate = "b,banana,80" + System.lineSeparator()
                + "s,banana,10" + System.lineSeparator()
                + "p,banana,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "b,apple,50" + System.lineSeparator()
                + "s,apple,20" + System.lineSeparator()
                + "r,apple,5" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,40";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_DATE_NAME))) {
            bufferedWriter.write(fruitShopDate);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + FILE_DATE_NAME, e);
        }
    }

    @Test
    public void readFromFile_readFruitShopFile_ok() {
        String actual = readDataService.readFromFile(FILE_DATE_NAME).get(2);
        String expected = "p,banana,20";
        assertEquals(actual, expected);
    }

    @Test
    public void readFromFile_notExistsFile_notOk() {
        try {
            readDataService.readFromFile("Fruit.csv");
        } catch (RuntimeException e) {
            return;
        }
        fail("Cant trow RuntimeException");
    }
}
