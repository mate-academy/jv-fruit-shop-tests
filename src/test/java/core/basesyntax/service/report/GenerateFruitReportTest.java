package core.basesyntax.service.report;

//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

//import core.basesyntax.dao.StorageDaoImpl;
//import core.basesyntax.model.FruitModel;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenerateFruitReportTest {
    private static GenerateFruitReport generateFruitReport; // = new GenerateFruitReport();

    @BeforeClass
    public static void beforeClass() throws Exception {
        generateFruitReport = new GenerateFruitReport();
    }

    @Test
    public void generateLine_True() {
        StringBuilder expectedResult = new StringBuilder("apple,10");
        StringBuilder actualResult = generateFruitReport.generateResultForCommodity("apple", 10);
        assertEquals(actualResult.toString(), expectedResult.toString());
    }
    /*
    @Test
    public void getReport_True() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        String expectedResult = "fruit,quantity" + System.lineSeparator() + "apple,10";
        assertTrue(generateFruitReport.getReport().equals(expectedResult));
    }
    */
}
