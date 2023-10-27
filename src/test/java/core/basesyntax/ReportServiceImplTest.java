package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void setUp() {
        reportService = new ReportServiceImpl(new FruitDaoImpl());
    }

    @Test
    void createReport_notEmptyStorage_ok() {
        String fruit = "banana";
        int quantity = 12;
        FruitStorage.fruits.put(fruit, quantity);

        String expected = "fruit,quantity\n" + fruit + "," + quantity;

        String actual = reportService.createReport();

        assertEquals(expected, actual, "Returned string must be equals with the expected!");
    }

    @Test
    void createReport_emptyStorage_ok() {
        FruitStorage.fruits.clear();

        String expected = "fruit,quantity";

        String actual = reportService.createReport();

        assertEquals(expected, actual, "Returned string must be equals with the expected!");
    }

}
