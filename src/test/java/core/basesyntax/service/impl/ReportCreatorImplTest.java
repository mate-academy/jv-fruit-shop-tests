package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ReportCreator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator<FruitTransaction> reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        Storage.storage.clear();
        Storage.storage.put("apple",90);
        Storage.storage.put("banana",45);
    }

    @Test
    void createReport_validInput_ok() {
        List<FruitTransaction> fruitTransactions = Arrays.asList(
                new FruitTransaction(Operation.PURCHASE, "banana", 20),
                new FruitTransaction(Operation.RETURN, "apple", 48)
        );

        String actualReport = reportCreator.createReport(Storage.storage, fruitTransactions);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator()
                + "apple,138";
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_nullList_notOk() {
        Map<String, Integer> report = new HashMap<>();
        assertThrows(RuntimeException.class,
                () -> reportCreator.createReport(report, null));
    }

    @Test
    void createReport_negativeQuantity_notOk() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.PURCHASE, "apple", 10)
        );

        Map<String, Integer> report = new HashMap<>();
        report.put("apple", -5);

        assertThrows(RuntimeException.class,
                () -> reportCreator.createReport(report, transactions));
    }
}
