package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final ReportService REPORT_SERVICE = new ReportServiceImpl();

    @Test
    void storageToString_correctStorageData_ok() {
        FruitStorage.FRUIT_STORAGE.put("banana",10);
        FruitStorage.FRUIT_STORAGE.put("apple",20);
        String correctStorageData = "fruit,quantity\nbanana,10\napple,20";
        String actual = REPORT_SERVICE.storageToString();
        assertEquals(correctStorageData,actual);
    }

    @Test
    void storageToString_emptyStorage_ok() {
        String correctStorageData = "fruit,quantity\n";
        String actual = REPORT_SERVICE.storageToString();
        assertEquals(correctStorageData,actual);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.FRUIT_STORAGE.clear();
    }
}
