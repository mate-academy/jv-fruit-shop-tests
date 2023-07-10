package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_ORANGE = "orange";
    private static final Integer AMOUNT_APPLE = 10;
    private static final Integer AMOUNT_ORANGE = 15;
    private ReportService reportService = new ReportServiceImpl();

    @Before
    public void setUp() {
        Storage.clearStorage();
        Storage.putFruitToStorage(FRUIT_APPLE, AMOUNT_APPLE);
        Storage.putFruitToStorage(FRUIT_ORANGE, AMOUNT_ORANGE);
    }

    @Test
    public void processDailyReport_validData_ok() {
        List<String> storage = Storage
                .getStorage().entrySet()
                .stream()
                .map(t -> t.getKey()
                        + FruitTransactionParserImpl.SEPARATE_SYMBOL_FOR_CSV
                        + t.getValue()).collect(Collectors.toList());
        storage.add(0,ReportService.HEAD_OF_REPORT_TABLE);
        assertEquals(storage, reportService.createReport());
    }
}
