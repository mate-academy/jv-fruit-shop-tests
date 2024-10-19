package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import database.Storage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

class ReportGeneratorTest {
    private static final String TITLE = "fruit,quantity";
    private static ReportGenerator generator;
    private FruitTransaction appleTransaction;
    private FruitTransaction bananaTransaction;
    private FruitTransaction orangeTransaction;

    @BeforeAll
    static void beforeAll() {
        generator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        appleTransaction = new FruitTransaction("b", "apple", 15);
        bananaTransaction = new FruitTransaction("b", "banana", 36);
        orangeTransaction = new FruitTransaction("b", "orange", 19);
        Storage.addToStorage(appleTransaction.getFruit(),
                appleTransaction.getQuantity());
        Storage.addToStorage(bananaTransaction.getFruit(),
                bananaTransaction.getQuantity());
        Storage.addToStorage(orangeTransaction.getFruit(),
                orangeTransaction.getQuantity());
    }

    @AfterEach
    void tearDown() {
        Storage.clearStorage();
    }

    @Test
    void tryGenerateReport_Ok() {
        String actualReport = generator.getReport();
        Set<String> actualSet = new HashSet<>(
                Arrays.asList(actualReport.split(System.lineSeparator())));
        Set<String> expectedSet = new HashSet<>(
                Arrays.asList(TITLE, "apple,15", "banana,36", "orange,19"));
        assertEquals(expectedSet, actualSet);
    }
}
