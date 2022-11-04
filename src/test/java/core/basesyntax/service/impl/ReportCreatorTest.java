package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.CreatReport;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorTest {
    private CreatReport creator;
    private FruitStorageDao storageDao;

    @Before
    public void setUp() throws Exception {
        storageDao = new FruitStorageDaoImpl();
        creator = new ReportCreator(storageDao);
    }

    @Test
    public void creatReport_storageIsNull_NotOk() {
        storageDao = null;
        try {
            creator = new ReportCreator(storageDao);
        } catch (RuntimeException e) {
            assertEquals("No storage is not matched", e.getMessage());
        }
    }

    @Test
    public void creatReport_rightReport_Ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 90);
        List<String> rightReport = List.of("banana,152" + System.lineSeparator(),
                "apple,90" + System.lineSeparator());
        List<String> createdReport = creator.creatReport();
        assertEquals(rightReport, createdReport);
    }

    @Test
    public void creatReport_storageIsEmpty_Ok() {
        List<String> rightReport = new ArrayList<>();
        List<String> createdReport = creator.creatReport();
        assertEquals(rightReport, createdReport);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
