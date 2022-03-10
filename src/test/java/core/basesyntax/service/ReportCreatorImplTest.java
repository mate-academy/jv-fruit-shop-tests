package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;
    private List<Fruit> fruits = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void createReport_identicalOk() {
        Fruit fruitBanana = new Fruit();
        Fruit fruitApple = new Fruit();
        fruitBanana.setQuantity(152);
        fruitBanana.setName("banana");
        fruitApple.setName("apple");
        fruitApple.setQuantity(90);
        fruits.add(fruitBanana);
        fruits.add(fruitApple);
        String actual = reportCreator.createReport(fruits);
        String expected = "fruit,quantity\n"
                +
                "banana,152\n"
                +
                "apple,90\n";
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createReport_nullArgument_ok() {
        ReportCreator reportCreator = new ReportCreatorImpl();
        reportCreator.createReport(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createReport_fromEmptyList_notOk() {
        ReportCreator reportCreator = new ReportCreatorImpl();
        List<Fruit> emptyFruitList = new ArrayList<>();
        reportCreator.createReport(emptyFruitList);
    }
}



