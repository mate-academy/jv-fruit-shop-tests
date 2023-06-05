package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.SurplusCalculator;
import core.basesyntax.storage.TemporaryStorage;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SurplusCalculatorImplTest {
    private static SurplusCalculator surplusCalculator;

    @BeforeAll
    static void beforeAll() {
        surplusCalculator = new SurplusCalculatorImpl();
    }

    @AfterEach
    void tearDown() {
        TemporaryStorage.temporaryStorage.clear();
    }

    @Test
    void calculateData_properListTwoOperation_ok() {
        List<FruitTransaction> dailyOperationTransactions = List.of(
                new FruitTransaction(BALANCE, "banana", 450),
                new FruitTransaction(PURCHASE, "banana", 80));
        surplusCalculator.calculateData(dailyOperationTransactions);
        Map<String, Integer> expected = Map.of("banana", 370);
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void calculateData_emptyList_notOk() {
        List<FruitTransaction> dailyOperationTransactions = Collections.emptyList();
        assertThrows(RuntimeException.class,
                () -> surplusCalculator.calculateData(dailyOperationTransactions),
                "\"RuntimeException\" should be thrown when the input list is empty");
    }

    @Test
    void calculateData_nullList_notOk() {
        List<FruitTransaction> dailyOperationTransactions = null;
        assertThrows(RuntimeException.class,
                () -> surplusCalculator.calculateData(dailyOperationTransactions),
                "\"RuntimeException\" should be thrown when the input list is null");
    }
}
