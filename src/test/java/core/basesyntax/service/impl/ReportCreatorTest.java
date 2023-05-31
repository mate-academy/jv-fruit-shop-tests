package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.CreatReport;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorTest {
    private CreatReport creator;

    @Before
    public void setUp() {
        creator = new ReportCreator(new FruitStorageDaoImpl());
    }

    @Test
    public void createReport_storageIsNull_NotOk() {
        try {
            creator = new ReportCreator(null);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("No storage is not matched", e.getMessage());
        }
    }

    @Test
    public void createReport_rightReport_Ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 90);
        List<String> rightReport = List.of("banana,152" + System.lineSeparator(),
                "apple,90" + System.lineSeparator());
        List<String> createdReport = creator.creatReport();
        assertEquals(rightReport, createdReport);
    }

    @Test
    public void createReport_storageIsEmpty_Ok() {
        List<String> createdReport = creator.creatReport();
        assertEquals(Collections.emptyList(), createdReport);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
