package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int INITIAL_APPLE_QUANTITY = 5;
    private static final int INITIAL_BANANA_QUANTITY = 10;
    private ReportCreator reportCreator;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        fruitDao = new FruitDaoImpl();
        Storage.fruits.clear();
    }

    @Test
    void createReportWithOneFruit_Ok() {
        fruitDao.add(APPLE, INITIAL_APPLE_QUANTITY);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,5" + System.lineSeparator();
        String actualReport = reportCreator.createReport();
        assertEquals(expectedReport, actualReport,
                "The report should match the expected output for a single fruit");
    }

    @Test
    void createReportWithTwoFruits_Ok() {
        fruitDao.add(APPLE, INITIAL_APPLE_QUANTITY);
        fruitDao.add(BANANA, INITIAL_BANANA_QUANTITY);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,5" + System.lineSeparator()
                + "banana,10" + System.lineSeparator();

        String actualReport = reportCreator.createReport();

        String sortedExpectedReport = Arrays.stream(expectedReport.split(System.lineSeparator()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));

        String sortedActualReport = Arrays.stream(actualReport.split(System.lineSeparator()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));

        assertEquals(sortedExpectedReport, sortedActualReport,
                "The report should match the expected output");
    }

    @Test
    void getReportEmptyStorage_NotOk() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportCreator.createReport();
        assertEquals(expectedReport, actualReport,
                "The report for empty storage should only contain the header");
    }
}
