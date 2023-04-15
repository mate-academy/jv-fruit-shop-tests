package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FinalReportTest {
    private static final String FRUIT = "banana";
    private static final String SECOND_FRUIT = "apple";
    private static final int FRUIT_QUANTITY = 20;
    private static final int SECOND_FRUIT_QUANTITY = 10;
    private static final List<String> TEST_LIST =
            List.of("fruit,quantity", "banana,20", "apple,10");
    private static FinalReport finalReport;

    @BeforeAll
    static void beforeAll() {
        finalReport = new FinalReport();
    }

    @Test
    void getReport_Ok() {
        Storage.getFruitsStorage().put(FRUIT, FRUIT_QUANTITY);
        Storage.getFruitsStorage().put(SECOND_FRUIT, SECOND_FRUIT_QUANTITY);
        List<String> actual = finalReport.getReport();
        Assert.assertEquals(TEST_LIST, actual);
    }
}
