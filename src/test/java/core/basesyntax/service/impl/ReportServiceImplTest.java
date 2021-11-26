package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportServiceImplTest {
    private final ReportService reportService = new ReportServiceImpl();
    
    @Test
    public void createReport_validReport_ok() {
        Map<Fruit, Integer> map = new HashMap<>();
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        map.put(apple, 90);
        map.put(banana, 152);
        String expected = "fruit,quantity\napple,90\nbanana,152\n";
        String actual = reportService.createReport(map);
        assertEquals(expected, actual);
    }
}
