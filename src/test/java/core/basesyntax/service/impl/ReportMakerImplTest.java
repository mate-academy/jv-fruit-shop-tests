package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMaker;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerImplTest {
    private static ActionsDao actionsDao;
    private static ReportMaker prepareReport;

    @BeforeClass
    public static void beforeClass() {
        actionsDao = new ActionsDaoImpl(Storage.data);
        prepareReport = new ReportMakerImpl(actionsDao);
    }

    @Before
    public void setUp() {
        actionsDao.clear();
    }

    @Test
    public void reportMaker_createEmptyReport_ok() {
        String expected = "fruit,quantity";
        String actual = prepareReport.makeReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportMaker_createValidReport_ok() {
        String expected = "fruit,quantity" + System.lineSeparator() + "apple" + "," + "15";
        actionsDao.add("apple", 15);
        String actual = prepareReport.makeReport();
        assertEquals(expected, actual);
    }
}
