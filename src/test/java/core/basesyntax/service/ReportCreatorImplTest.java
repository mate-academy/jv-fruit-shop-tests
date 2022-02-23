package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator reportCreator;
    private List<Fruit> fruits = new ArrayList<>();

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReportIdenticalOk() {
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createReportNullArgument() {
        ReportCreator reportCreator = new ReportCreatorImpl();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reportCreator.createReport(null);
        });
    }

    @Test
    void createReportFromEmptyList() {
        ReportCreator reportCreator = new ReportCreatorImpl();
        List<Fruit> emptyFruitList = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reportCreator.createReport(emptyFruitList);
        });
    }
}


