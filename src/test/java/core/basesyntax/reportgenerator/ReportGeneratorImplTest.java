package core.basesyntax.reportgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String REPORT_FIRST_LINE = "fruit,quantity";
    private static FruitsDao dao;
    private static ReportGenerator gen;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitsDaoImpl();
        gen = new ReportGeneratorImpl(dao);
    }

    @Test
    void createReport_validArgs_ok() {
        dao.addFruitToStorage("apple", 5);

        String expected = REPORT_FIRST_LINE
                + System.lineSeparator()
                + "apple,5";
        String actual = gen.getReport();

        assertEquals(expected, actual, "The result string is not correct");
    }

    @Test
    void createReport_nullArgs_ok() {
        String actual = gen.getReport();

        assertEquals(REPORT_FIRST_LINE, actual, "The result string is not correct");
    }
}
