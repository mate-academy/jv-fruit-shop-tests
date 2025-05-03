package core.basesyntax.service.workwithfile;

import static org.junit.Assert.assertNotNull;

import core.basesyntax.service.SaveDataToStorage;
import core.basesyntax.service.impl.SaveDataToStorageImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportImplTest {
    private static Report report;

    @BeforeClass
    public static void beforeClass() throws Exception {
        report = new ReportImpl();
    }

    @Test
    public void reportTest_Ok() {
        SaveDataToStorage saveDataToStorage = new SaveDataToStorageImpl();
        assertNotNull(report.report(saveDataToStorage));
    }
}
