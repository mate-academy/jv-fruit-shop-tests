package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static ReportGeneratorService reportGeneratorService;

    @Before
    public void setUp() throws Exception {
        reportGeneratorService = new ReportGeneratorServiceImpl();
        Storage.fruits.clear();
    }

    @Test
    public void generate_header_ok() {
        List<String> actual = reportGeneratorService.generate();
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals("fruit,quantity", actual.get(0));
    }

    @Test
    public void generate_list_ok() {
        Storage.fruits.put("watermelon", 99);
        List<String> actual = reportGeneratorService.generate();
        Assert.assertEquals(2, actual.size());
        Assert.assertEquals("watermelon,99", actual.get(1));
    }
}
