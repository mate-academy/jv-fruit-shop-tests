package core.basesyntax.servise.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.ReportGeneratorService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static ReportGeneratorService generatorService;

    @BeforeClass
    public static void beforeClass() {
        generatorService = new ReportGeneratorServiceImpl();
    }

    @After
    public void after() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void generate_report_Ok() {
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator()
                + "apple,15"
                + System.lineSeparator();
        Storage.fruitStorage.put("banana", 20);
        Storage.fruitStorage.put("apple", 15);
        String actual = generatorService.generate();
        Assert.assertEquals(expected, actual);
    }
}
