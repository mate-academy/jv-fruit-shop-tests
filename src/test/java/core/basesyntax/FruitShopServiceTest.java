package core.basesyntax;

import core.basesyntax.service.impl.FruitShopServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

import java.io.File;



/**
 * Feel free to remove this class and create your own.
 */
public class FruitShopServiceTest {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public FruitShopService fruitShopService = new FruitShopServiceImpl();
    private static final String FILE_SEPARATOR = File.separator;
    private static final String RESOURCES_PATH = "src" + FILE_SEPARATOR
            + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR;
    private static final String FULL_CSV = RESOURCES_PATH + "FULL_CSV.csv";
    private static final String WRONG_CSV_PATH = RESOURCES_PATH + "WRONG_CSV_PATH.csv";
    private static final String WRONG_QUANTITY_CSV_PATH = RESOURCES_PATH + "WRONG_QUANTITY_CSV.csv";
    private static final String WRONG_VALUE_CSV_PATH = RESOURCES_PATH + "WRONG_VALUE_CSV.csv";
    private static final String WRONG_SUMMARY_VALUE_CSV_PATH = RESOURCES_PATH + "WRONG_SUMMARY_VALUE_CSV.csv";

    @Rule
    public ExpectedException expectedException = new ExpectedException().reportMissingExceptionWithMessage();

    @Test
    public void getCorrectReport_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR
                + "banana,152"+ LINE_SEPARATOR;
        String actual = fruitShopService.getReport(FULL_CSV);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getReportWithWrongPath_Not_OK() {
        fruitShopService.getReport(WRONG_CSV_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void getReportWithWrongQuantity_Not_OK() {
        fruitShopService.getReport(WRONG_QUANTITY_CSV_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void getReportWithWrongValues_Not_OK() {
        fruitShopService.getReport(WRONG_VALUE_CSV_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void getReportWithWrongSummaryValues_Not_OK() {
        fruitShopService.getReport(WRONG_SUMMARY_VALUE_CSV_PATH);
    }

    @Test
    public void getSameObjectFromStorage() {

    }

}
