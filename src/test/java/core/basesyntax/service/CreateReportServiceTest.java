package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.CreateReportServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportServiceTest {
    private static List<Fruit> fruits;
    private static CreateReportService createReportService;

    @BeforeClass
    public static void beforeClass() {
        fruits = new ArrayList<>();
        createReportService = new CreateReportServiceImpl();
    }

    @Before
    public void beforeEachTest() {
        fruits.add(new Fruit("apple", 65));
        fruits.add(new Fruit("pear", 45));
        fruits.add(new Fruit("orange", 15));
    }

    @Test
    public void createReport_threeFruits_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,65" + System.lineSeparator()
                + "pear,45" + System.lineSeparator()
                + "orange,15";
        String actual = createReportService.createReport(fruits);
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}
