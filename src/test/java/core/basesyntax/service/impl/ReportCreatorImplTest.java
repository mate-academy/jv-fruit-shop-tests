package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        FruitStorage.fruitInventory.clear();
    }

    @Test
    void create_Report_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,30";
        FruitStorage.addFruit("banana", 20);
        FruitStorage.addFruit("apple", 30);
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void create_EmptyReport_Ok() {
        String expected = "fruit,quantity";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}
