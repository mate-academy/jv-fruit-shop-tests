package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final int VALID_FIRST_QUANTITY = 152;
    private static final int VALID_SECOND_QUANTITY = 240;
    private static final int VALID_THIRD_QUANTITY = 400;
    private static final int VALID_FOURTH_QUANTITY = 250;
    private static final String VALID_FIRST_FRUIT = "banana";
    private static final String VALID_SECOND_FRUIT = "apple";
    private static final String VALID_THIRD_FRUIT = "lemon";
    private static final String VALID_FOURTH_FRUIT = "strawberry";

    private static ReportCreatorService reportCreatorService;

    private static final List<String> validReport = List.of(
            "fruit,quantity",
            "banana,152",
            "apple,240",
            "lemon,400",
            "strawberry,250");

    @BeforeClass
    public static void beforeAll() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.records.clear();
    }

    @Test
    public void reportCreator_validData_ok() {
        Storage.records.put(VALID_FIRST_FRUIT, VALID_FIRST_QUANTITY);
        Storage.records.put(VALID_SECOND_FRUIT, VALID_SECOND_QUANTITY);
        Storage.records.put(VALID_THIRD_FRUIT, VALID_THIRD_QUANTITY);
        Storage.records.put(VALID_FOURTH_FRUIT, VALID_FOURTH_QUANTITY);
        List<String> report = reportCreatorService.create();
        assertEquals("Reports are not equals ", validReport, report);
    }

    @Test
    public void reportCreator_invalidData_notOk() {
        Storage.records.put(VALID_FIRST_FRUIT, VALID_FIRST_QUANTITY);
        Storage.records.put(VALID_SECOND_FRUIT, VALID_SECOND_QUANTITY);
        Storage.records.put(VALID_FOURTH_FRUIT, VALID_FOURTH_QUANTITY);
        List<String> generatedReport = reportCreatorService.create();
        assertNotEquals("Reports are equals ", validReport, generatedReport);
    }
}
