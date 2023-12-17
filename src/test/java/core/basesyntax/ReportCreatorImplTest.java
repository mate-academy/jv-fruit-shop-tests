package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.jupiter.api.Test;

public class ReportCreatorImplTest {
    private static final String FIRST_FRUIT = "apple";
    private static final String SECOND_FRUIT = "raspberry";
    private static final int FIRST_AMOUNT = 122;
    private static final int SECOND_AMOUNT = 852;
    private static final String EXPECTED_REPORT =
            "fruit,quantity" + System.lineSeparator()
                    + "apple,122" + System.lineSeparator()
                    + "raspberry,852";
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @Test
    void createReport_storageData_ok() {
        FruitStorage.fruits.clear();
        FruitStorage.fruits.put(FIRST_FRUIT, FIRST_AMOUNT);
        FruitStorage.fruits.put(SECOND_FRUIT, SECOND_AMOUNT);
        String actualReport = reportCreator.createReport();
        assertEquals(EXPECTED_REPORT, actualReport);
    }
}
