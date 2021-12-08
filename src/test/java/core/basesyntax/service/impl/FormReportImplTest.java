package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FormReport;
import org.junit.Test;

public class FormReportImplTest {
    private static final String VALID_RESULT = "fruit,quantity\r\nbanana,152\r\napple,90";
    private final FormReport formReport = new FormReportImpl();

    @Test
    public void checkReport() {
        Storage.storage.put(new Fruit("banana"), 152);
        Storage.storage.put(new Fruit("apple"), 90);
        assertEquals(formReport.reportFromStorage(), VALID_RESULT);
    }

    @Test
    public void checkNotValidReport() {
        Storage.storage.clear();
        assertNotEquals(formReport.reportFromStorage(), VALID_RESULT);
    }
}
