package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.impl.FruitDaoImpl;
import db.FruitStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest { private final ReportGeneratorImpl
        reportGenerator = new ReportGeneratorImpl(new FruitDaoImpl());

    @BeforeEach
    void setUp() {
        FruitStorage.fruits.clear();
    }

    @Test
    void getReport_validStorage_ok() {
        FruitStorage.fruits.put("banana", 20);
        FruitStorage.fruits.put("apple", 100);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,20";

        assertEquals(expected.trim(), reportGenerator.getReport().trim());
    }
}
