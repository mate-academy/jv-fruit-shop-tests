package core.basesyntax.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.interfaces.FruitReportCreate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitReportCreateImplTest {
    private static final List<String> EXPECTED_VALID_REPORT = List.of(
            "fruit,quantity",
            "banana,107",
            "apple,100"
    );

    private static final String EXPECTED_EMPTY_REPORT = "fruit,quantity" + System.lineSeparator();
    private StorageDao storageDao;
    private FruitReportCreate reportService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        reportService = new FruitReportCreateImpl(storageDao);
        Storage.fruitsQuantity.clear();
    }

    @Test
    void create_reportValidData_Ok() {
        storageDao.add("banana", 107);
        storageDao.add("apple", 100);

        String actualReport = reportService.createReport();
        String[] actualLines = actualReport.split(System.lineSeparator());
        assertIterableEquals(EXPECTED_VALID_REPORT, List.of(actualLines));
    }

    @Test
    void create_reportNoDataInStorage_notOk() {
        assertEquals(EXPECTED_EMPTY_REPORT, reportService.createReport());
    }

    @Test
    void create_reportNullStorageInstance_notOk() {
        reportService = new FruitReportCreateImpl(null);
        assertEquals(EXPECTED_EMPTY_REPORT, reportService.createReport());
    }
}
