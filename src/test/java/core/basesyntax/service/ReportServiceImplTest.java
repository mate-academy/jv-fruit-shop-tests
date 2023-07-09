package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    @Before
    public void setUp() {
        Storage.clearStorage();
        Storage.putFruitToStorage("apple", 10);
        Storage.putFruitToStorage("orange", 15);
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
        assertEquals(storage, new ReportServiceImpl().createReport());
    }
}
