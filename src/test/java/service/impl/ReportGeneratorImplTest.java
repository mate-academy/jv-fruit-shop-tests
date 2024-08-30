package service.impl;

import dao.FruitDao;
import dao.FruitDaoImpl;
import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;
import util.TestConstants;

class ReportGeneratorImplTest {
    private static FruitDao fruitDao;
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitDao);
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put(TestConstants.BANANA, 20);
        Storage.fruitStock.put(TestConstants.APPLE, 100);
    }

    @Test
    void getReport_validData_isOk() {
        StringBuilder report = new StringBuilder();
        report.append(TestConstants.REPORT_HEADER).append(System.lineSeparator())
                .append(TestConstants.BANANA_BALANCE).append(System.lineSeparator())
                .append(TestConstants.APPLE_BALANCE);
        String expected = report.toString();
        String actual = reportGenerator.getReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getReport_incorrectData_isOk() {
        Storage.fruitStock.put(TestConstants.EMPTY_VALUE, 20);
        StringBuilder report = new StringBuilder();
        report.append(TestConstants.HEADER).append(System.lineSeparator())
                .append(TestConstants.BANANA_BALANCE).append(System.lineSeparator())
                .append(TestConstants.APPLE_BALANCE);
        String expected = report.toString();
        String actual = reportGenerator.getReport();
        Assertions.assertNotEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
