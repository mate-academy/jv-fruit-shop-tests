package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitBalanceDao;
import core.basesyntax.dao.FruitBalanceDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitBalance;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        FruitBalanceDao fruitBalanceDao = new FruitBalanceDaoImpl();
        generator = new ReportGeneratorImpl(fruitBalanceDao);
    }

    @Test
    void getReport_validFruitBalanceList_ok() {
        assertTrue(Storage.fruitBalances.isEmpty());
        Storage.fruitBalances.add(new FruitBalance("banana", 100));
        Storage.fruitBalances.add(new FruitBalance("apple", 50));
        Storage.fruitBalances.add(new FruitBalance("kiwi", 40));
        String expected = String.join(System.lineSeparator(),
                "banana,100", "apple,50", "kiwi,40");
        String actual = generator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyList_ok() {
        String expected = "";
        String actual = generator.getReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    public void tearDown() {
        Storage.fruitBalances.clear();
    }
}
