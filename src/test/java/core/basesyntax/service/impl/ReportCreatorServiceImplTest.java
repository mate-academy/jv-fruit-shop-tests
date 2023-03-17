package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final List<String> VALID_REPORT = List.of(
            HEADER,
            "\nbanana,11",
            "\napple,25"
    );
    private final ReportCreatorService reportCreatorService;

    public ReportCreatorServiceImplTest() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void getReport_validData_Ok() {
        Storage.storage.put("banana", 11);
        Storage.storage.put("apple", 25);
        Assert.assertEquals(VALID_REPORT, reportCreatorService.getReport());
        Storage.storage.clear();
    }

    @Test
    public void getReport_emptyStorage_Ok() {
        Assert.assertEquals(List.of(HEADER), reportCreatorService.getReport());
    }
}
