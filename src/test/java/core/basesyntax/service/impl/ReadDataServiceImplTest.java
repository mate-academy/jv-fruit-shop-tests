package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReadDataService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadDataServiceImplTest {
    private static final String FILE_DATE_NAME = "src/main/java/resources/FruitShop.csv";
    private static ReadDataService readDataService;

    @BeforeClass
    public static void beforeClass() {
        readDataService = new ReadDataServiceImpl();
    }

    @Test
    public void readData_readFruitShopFile_ok() {
        String actual = readDataService.readFromFile(FILE_DATE_NAME).get(2);
        String expected = "p,banana,20";
        assertEquals(actual, expected);
    }

    @Test
    public void readData_notExistsFile_notOk() {
        try {
            readDataService.readFromFile("Fruit.csv");
        } catch (RuntimeException e) {
            return;
        }
        fail("Cant trow RuntimeException");
    }
}
