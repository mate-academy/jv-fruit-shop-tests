package service.impl;

import dao.FruitDao;
import db.Storage;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDao() {
            @Override
            public Integer getBalance(String fruit) {
                return 0;
            }

            @Override
            public boolean addBalance(String fruit, int quantity) {
                return false;
            }

            @Override
            public void updateBalance(String fruit, int quantity) {

            }

            @Override
            public Set<Map.Entry<String, Integer>> getAllEntries() {
                return Storage.fruitStock.entrySet();
            }
        };
        reportGenerator = new ReportGeneratorImpl(fruitDao);
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put("banana", 20);
        Storage.fruitStock.put("apple", 100);
    }

    @Test
    void getReport_validData_isOk() {
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,20").append(System.lineSeparator())
                .append("apple,100");
        String expected = report.toString();
        String actual = reportGenerator.getReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getReport_incorrectData_isOk() {
        Storage.fruitStock.put("", 20);
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,20").append(System.lineSeparator())
                .append("apple,100");
        String expected = report.toString();
        String actual = reportGenerator.getReport();
        Assertions.assertNotEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
