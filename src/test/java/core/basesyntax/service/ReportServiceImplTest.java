package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import service.ReportService;

public class ReportServiceImplTest {
    private ReportService service;
    private List<Fruit> fruits;

    @Test
    public void report_service_ok() {
        fruits = new ArrayList<>();
        fruits.add(new Fruit("apple", 25));
        fruits.add(new Fruit("banana", 23));
        service = new ReportServiceImpl();
        String actual = service.createReport(fruits);
        StringBuilder builder = new StringBuilder();
        String expected = builder.append("fruit,quantity").append(System.lineSeparator())
                .append("apple,25").append(System.lineSeparator())
                .append("banana,23").toString();
        Assert.assertEquals(expected, actual);
    }
}
