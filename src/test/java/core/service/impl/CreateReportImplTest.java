package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.model.Fruit;
import core.service.CreateReport;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportImplTest {
    private static List<Fruit> fruits;
    private static CreateReport createReport;

    @BeforeClass
    public static void beforeClass() {
        fruits = new ArrayList<>();
        createReport = new CreateReportImpl();
    }

    @Before
    public void beforeEachTest() {
        fruits.add(new Fruit("apple", 85));
        fruits.add(new Fruit("pineapple", 12));
        fruits.add(new Fruit("blackberry", 32));
    }

    @Test
    public void createReport_correctWork_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,85" + System.lineSeparator()
                + "pineapple,12" + System.lineSeparator()
                + "blackberry,32";
        String actual = createReport.createReport(fruits);
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}
