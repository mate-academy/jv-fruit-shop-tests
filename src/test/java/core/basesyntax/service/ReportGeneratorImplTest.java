package core.basesyntax.service;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new core.basesyntax.serviceimpl.ReportGeneratorImpl();
        FruitStorage.getFruits().clear();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.getFruits().clear();
    }

    @Test
    void report_validInput_ok() {
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator() + "apple,20";

        FruitStorage.getFruits().put("banana", 100);
        FruitStorage.getFruits().put("apple", 20);

        String actual = reportGenerator.createReport();

        Assertions.assertEquals(expected, actual);
    }
}

