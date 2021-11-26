package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReportService;

public class ReportServiceImplTest {
    private static ReportService service;
    private List<Fruit> fruits;

    @BeforeClass
    public static void init() {
        service = new ReportServiceImpl();
    }

    @Before
    public void init_before() {
        fruits = new ArrayList<>();
    }

    @Test
    public void report_service_ok() {
        fruits.add(new Fruit("apple", 25));
        fruits.add(new Fruit("banana", 23));
        String actual = service.createReport(fruits);
        StringBuilder builder = new StringBuilder();
        String expected = builder.append("fruit,quantity").append(System.lineSeparator())
                .append("apple,25").append(System.lineSeparator())
                .append("banana,23").toString();
        Assert.assertEquals(expected, actual);
    }
}
