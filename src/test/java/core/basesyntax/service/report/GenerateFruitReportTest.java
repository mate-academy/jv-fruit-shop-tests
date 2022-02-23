package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.storage.FruitStorage;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenerateFruitReportTest {
    private static GenerateFruitReport generateFruitReport;

    @BeforeClass
    public static void beforeClass() throws Exception {
        FruitStorage.fruitStorage.clear();
        generateFruitReport = new GenerateFruitReport();
    }

    @Test
    public void getReport_True() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        String expectedResult = "fruit,quantity" + System.lineSeparator() + "apple,10";
        assertTrue(generateFruitReport.getReport().equals(expectedResult));
    }

    @Test
    public void generateResultForCommodity_generateLine_True() {
        StringBuilder expectedResult = new StringBuilder("apple,10");
        StringBuilder actualResult = generateFruitReport.generateResultForCommodity("apple", 10);
        assertEquals(actualResult.toString(), expectedResult.toString());
    }
}
