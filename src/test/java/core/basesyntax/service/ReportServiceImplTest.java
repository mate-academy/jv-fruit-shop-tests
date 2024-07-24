package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

    @AfterEach
    void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    @DisplayName("Report generating test")
    void reportGenerating_ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                                           FruitTransaction.FruitName.APPLE,
                                                           10));
        fruitDao.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                                           FruitTransaction.FruitName.BANANA,
                                                           20));
        String expectedReport = "fruit,quantity"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator();
        ReportService reportService = new ReportServiceImpl();
        String actualReport = reportService.generateReport();
        assertEquals(expectedReport, actualReport);
    }

}
