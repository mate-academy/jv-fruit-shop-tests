package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDB;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String FIRST_FRUIT = "banana";
    private static final String SECOND_FRUIT = "apple";
    private static final int FIRST_QUANTITY = 20;
    private static final int SECOND_QUANTITY = 59;
    private static final List<String> expected =
            Arrays.asList("fruits,quantity", "apple,59", "banana,20");
    private final ReportServiceImpl reportService = new ReportServiceImpl();

    @Test
    void createReport_fruitDB_Ok() {
        FruitDB.getFruitDataBase().clear();
        FruitDB.getFruitDataBase().put(FIRST_FRUIT, FIRST_QUANTITY);
        FruitDB.getFruitDataBase().put(SECOND_FRUIT, SECOND_QUANTITY);
        List<String> actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
