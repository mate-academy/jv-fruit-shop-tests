package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.exception.InvalidInputDataException;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void handle_BalanceOperation_Ok() {
        String emptyReportExpected = "fruit,quantity";
        String emptyReport = reportCreator.createReport();
        assertEquals(emptyReportExpected, emptyReport);
        balanceOperation.handle("banana", 50);
        balanceOperation.handle("apple", 70);
        String expectedReportWithFigures = "fruit,quantity"
                + System.lineSeparator()
                + "banana,50"
                + System.lineSeparator()
                + "apple,70";
        String actualReportWithFigures = reportCreator.createReport();
        assertEquals(expectedReportWithFigures, actualReportWithFigures);
    }

    @Test
    void handle_balanceNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> balanceOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }

    @Test
    void handle_balanceNegativeInputFruitQuantity_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> balanceOperation.handle("banana", -5),
                "InvalidInputDataException expected to be thrown");
    }

    @AfterEach
    void storageClear() {
        Storage.storage.clear();
    }
}

