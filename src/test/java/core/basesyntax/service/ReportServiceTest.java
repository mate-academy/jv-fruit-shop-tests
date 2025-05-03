package core.basesyntax.service;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.OutFileStructure;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final String COMMA_SEPARATOR = ",";
    private static OutFileStructure outFileStructure =
            new OutFileStructure("fruit", "quantity");
    private static StringBuilder expectedData = new StringBuilder();

    @BeforeClass
    public static void beforeClass() {
        expectedData.append(outFileStructure.getFruit()).append(COMMA_SEPARATOR)
                .append(outFileStructure.getQuantity())
                .append(System.lineSeparator())
                .append("banana,11").append(System.lineSeparator())
                .append("orange,12").append(System.lineSeparator())
                .append("apple,13").append(System.lineSeparator())
                .append("kiwi,14");

        FruitsStorage.fruitsStorage.put("banana", 11);
        FruitsStorage.fruitsStorage.put("orange", 12);
        FruitsStorage.fruitsStorage.put("apple",13);
        FruitsStorage.fruitsStorage.put("kiwi", 14);
    }

    @Test
    public void make_stringForWriting_Ok() {
        FruitsStorage storage = new FruitsStorage();
        ReportService report = new ReportServiceImpl();
        String actualData = report.getDataReport(outFileStructure, storage);

        Assert.assertEquals("Report data is not correct",
                expectedData.toString(), actualData);
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}
