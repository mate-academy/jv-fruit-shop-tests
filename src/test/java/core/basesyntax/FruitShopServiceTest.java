package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import java.io.File;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static FruitShopService fruitShopService;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String RESOURCES_PATH = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator;
    private static final String CSV_WITH_APPLES_ONLY = RESOURCES_PATH
            + "CSV_WITH_APPLES_ONLY.csv";
    private static final String CSV_WITH_BANANAS_ONLY = RESOURCES_PATH
            + "CSV_WITH_BANANAS_ONLY.csv";
    private static final String CSV_WITH_LOST_ARGUMENTS = RESOURCES_PATH
            + "CSV_WITH_LOST_ARGUMENTS.csv";
    private static final String CSV_WITH_NEGATIVE_VALUE = RESOURCES_PATH
            + "CSV_WITH_NEGATIVE_VALUE.csv";
    private static final String CSV_WITH_ORDERED_VALUES = RESOURCES_PATH
            + "CSV_WITH_ORDERED_VALUES.csv";
    private static final String CSV_WITH_ORIGINAL_VALUES = RESOURCES_PATH
            + "CSV_WITH_ORIGINAL_VALUES.csv";
    private static final String CSV_WITH_SPACES_IN_VALUES = RESOURCES_PATH
            + "CSV_WITH_SPACES_IN_VALUES.csv";
    private static final String CSV_WITH_WRONG_FRUIT = RESOURCES_PATH
            + "CSV_WITH_WRONG_FRUIT.csv";
    private static final String CSV_WITH_WRONG_ORDERED_VALUES = RESOURCES_PATH
            + "CSV_WITH_WRONG_ORDERED_VALUES.csv";
    private static final String CSV_WITH_WRONG_SUMMARY_VALUE = RESOURCES_PATH
            + "CSV_WITH_WRONG_SUMMARY_VALUE.csv";
    private static final String CSV_WITH_WRONG_CSV_PATH = RESOURCES_PATH
            + "CSV_WITH_WRONG_CSV_PATH.csv";

    @BeforeClass
    public static void setup() {
        fruitShopService = new FruitShopServiceImpl();
    }

    @Before
    public void init() {
        Storage.fruitsStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithNullPath_NotOK() {
        fruitShopService.getReport(null);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithWrongCsvPath_Not_OK() {
        fruitShopService.getReport(CSV_WITH_WRONG_CSV_PATH);
    }

    @Test
    public void getReport_WithApplesOnly_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(CSV_WITH_APPLES_ONLY);
    }

    @Test
    public void getReport_WithBananasOnly_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(CSV_WITH_BANANAS_ONLY);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithLostArguments_Not_OK() {
        fruitShopService.getReport(CSV_WITH_LOST_ARGUMENTS);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithNegativeValue_Not_OK() {
        fruitShopService.getReport(CSV_WITH_NEGATIVE_VALUE);
    }

    @Test
    public void getReport_WithOrderedValues_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(CSV_WITH_ORDERED_VALUES);
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_WithOriginalValues_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(CSV_WITH_ORIGINAL_VALUES);
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_WithSpacesInValues_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(CSV_WITH_SPACES_IN_VALUES);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithWrongFruit_Not_OK() {
        fruitShopService.getReport(CSV_WITH_WRONG_FRUIT);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithWrongOrderedValues_Not_OK() {
        fruitShopService.getReport(CSV_WITH_WRONG_ORDERED_VALUES);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithWrongSummaryValues_Not_OK() {
        fruitShopService.getReport(CSV_WITH_WRONG_SUMMARY_VALUE);
    }

}
