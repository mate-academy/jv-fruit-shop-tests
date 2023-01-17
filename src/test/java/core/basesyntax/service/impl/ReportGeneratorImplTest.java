package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.ReportGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void init() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void generate_storageIsEmpty_ok() {
        String expected = ReportGeneratorImpl.REPORT_HEADER;
        String actual = reportGenerator.generate();
        assertEquals(String.format("Should return: %s, but was: %s",
                expected, actual), expected, actual);
    }

    @Test
    public void generate_storageIsFull_ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.updateQuantity("apple", 20);
        fruitDao.updateQuantity("banana", 15);
        String expected = new StringBuilder(ReportGeneratorImpl.REPORT_HEADER)
                .append(System.lineSeparator()).append("banana,15")
                .append(System.lineSeparator()).append("apple,20")
                .toString();
        String actual = reportGenerator.generate();
        assertEquals(String.format("Should return: %s, but was: %s",
                expected, actual), expected, actual);
    }
}
