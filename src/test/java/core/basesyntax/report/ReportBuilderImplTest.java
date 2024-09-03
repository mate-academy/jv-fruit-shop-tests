package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.FruitStorageImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportBuilderImplTest {
    private static FruitStorage fruitStorage = new FruitStorageImpl();
    private static final ReportBuilder reportBuilder = new ReportBuilderImpl();

    @Test
    void buildCorrectReport_ok() {
        fruitStorage.addFruit("mango", 200);
        fruitStorage.addFruit("banana", 100);

        String header = "fruit,quantity";
        String firstElement = "mango,200";
        String secondElement = "banana,100";

        String actual = reportBuilder.buildReport(fruitStorage);

        assertTrue(actual.contains(header));
        assertTrue(actual.contains(firstElement));
        assertTrue(actual.contains(secondElement));
    }

    @Test
    void buildReportWithNullStorage_notOk() {
        assertThrows(NullPointerException.class, () -> reportBuilder.buildReport(null));
    }

    @AfterEach
    void tearDown() {
        fruitStorage = new FruitStorageImpl();
    }
}
