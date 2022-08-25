package core.basesyntax;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ShopWorkingTest {
    private static final ShopService shopService = new ShopServiceImpl();
    private static final String APPLES_ORANGES_REPORT = "applesOrangesReport.csv";
    private static final String APPLES_GRAPES_REPORT = "applesGrapesReport.csv";
    private static final String APPLES_BANANAS_REPORT = "applesBananasReport.csv";
    private static final String BANANAS_ORANGES_REPORT = "bananasOrangesReport.csv";

    @After
    public void clearData() {
        DataBase.transitions.clear();
        DataBase.fruitsAmount.clear();
    }

    @Test
    public void getStatisticAboutApplesOranges_Ok() {
        shopService.servicing("applesOranges.csv", APPLES_ORANGES_REPORT);
        String actualResult = readFromFile(APPLES_ORANGES_REPORT).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "orange,75" + System.lineSeparator()
                + "apple,99";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutApplesGrapes_Ok() {
        shopService.servicing("applesGrapes.csv", APPLES_GRAPES_REPORT);
        String actualResult = readFromFile(APPLES_GRAPES_REPORT).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "apple,111" + System.lineSeparator()
                + "grape,14";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutApplesBananas_Ok() {
        shopService.servicing("applesBananas.csv", APPLES_BANANAS_REPORT);
        String actualResult = readFromFile(APPLES_BANANAS_REPORT).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,100";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutBananasOranges_Ok() {
        shopService.servicing("bananasOranges.csv", BANANAS_ORANGES_REPORT);
        String actualResult = readFromFile(BANANAS_ORANGES_REPORT).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,185" + System.lineSeparator()
                + "orange,75";
        Assert.assertEquals(expectedResult, actualResult);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }

}
