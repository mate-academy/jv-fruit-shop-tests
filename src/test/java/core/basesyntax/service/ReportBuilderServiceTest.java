package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageOfData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReportBuilderServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderServiceTest {
    private static ReportBuilderService reportBuilder;

    @BeforeClass
    public static void setUp() {
        reportBuilder = new ReportBuilderServiceImpl();
    }

    @Before
    public void clear() {
        StorageOfData.fruitsData.clear();
    }

    @Test
    public void reportBuilder_emptyStorage_ok() {
        String expected = "fruit,quantity";
        String actual = reportBuilder.buildReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportBuilder_trueBuild_ok() {
        FruitTransaction fruit = new FruitTransaction();
        fruit.setFruit("name");
        StringBuilder builder = new StringBuilder("fruit,quantity");
        String expected = builder.append(System.lineSeparator())
                .append(fruit.getFruit())
                .append(",")
                .append(10)
                .toString();
        System.out.println(expected);
        StorageOfData.fruitsData.put(fruit.getFruit(), 10);
        String actual = reportBuilder.buildReport();
        assertEquals(expected, actual);
    }
}
