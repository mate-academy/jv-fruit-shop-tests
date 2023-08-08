package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,15" + System.lineSeparator() + "apple,10";
    private static final String FIRST_FRUIT_NAME = "banana";
    private static final String SECOND_FRUIT_NAME = "apple";
    private static final int FIRST_FRUIT_QUANTITY = 15;
    private static final int SECOND_FRUIT_QUANTITY = 10;
    private ReportCreatorServiceImpl reportCreatorService;
    private FruitDao fruitDao;
    private Map<String, Integer> fruitData;

    @BeforeEach
    public void setUp() {
        Storage.inputData.clear();
        fruitDao = new FruitDaoImpl();
        reportCreatorService = new ReportCreatorServiceImpl(fruitDao);
        fruitData = new HashMap<>();
    }

    @Test
    public void createReport_successReporting_ok() {
        fruitData.put(FIRST_FRUIT_NAME, FIRST_FRUIT_QUANTITY);
        fruitData.put(SECOND_FRUIT_NAME, SECOND_FRUIT_QUANTITY);
        fruitDao.put(FIRST_FRUIT_NAME, FIRST_FRUIT_QUANTITY);
        fruitDao.put(SECOND_FRUIT_NAME, SECOND_FRUIT_QUANTITY);
        String report = reportCreatorService.createReport();
        assertEquals(EXPECTED_REPORT, report);
    }

    @Test
    public void createReport_EmptyData_notOk() {
        String expectedReport = "fruit,quantity";
        String report = reportCreatorService.createReport();
        assertEquals(expectedReport, report);
    }

    @Test
    public void testCreateReport_NullData_notOk() {
        String expectedReport = "fruit,quantity";
        fruitData = null;
        String report = reportCreatorService.createReport();
        assertEquals(expectedReport, report);
    }
}
