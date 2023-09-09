package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final String COMA = ",";
    public static final String FRUIT = "fruit";
    public static final String QUANTITY = "quantity";
    private Storage storage;
    private ReportServiceImpl reportService;

    @BeforeEach
    public void init() {
        storage = new Storage();
        reportService = new ReportServiceImpl(storage);
        Map<String, Integer> data = storage.getData();
        data.put("banana", 152);
        data.put("apple", 90);
    }

    @Test
    public void implementedCorrectInterface_OK() {
        List<Class<?>> interfaces = Arrays.asList(reportService.getClass().getInterfaces());
        Assertions.assertEquals(1, interfaces.size());
        Assertions.assertTrue(interfaces.contains(ReportService.class));
    }

    @Test
    public void createReport_validData_OK() {
        Map<String, Integer> data = storage.getData();
        StringBuilder expected = new StringBuilder();
        expected.append(FRUIT).append(COMA).append(QUANTITY);
        for (Map.Entry<String, Integer> map : data.entrySet()) {
            expected.append(System.lineSeparator())
                    .append(map.getKey())
                    .append(COMA)
                    .append(map.getValue());
        }
        String actual = reportService.createReport();
        Assertions.assertEquals(expected.toString(), actual);
    }
}
