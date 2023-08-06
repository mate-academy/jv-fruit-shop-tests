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

class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void handle_supplyOperation_Ok() {
        Storage.storage.put("banana", 50);
        Storage.storage.put("apple", 50);
        String actualInitialReport = reportCreator.createReport();
        String expectedInitialReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,50"
                + System.lineSeparator()
                + "apple,50";
        assertEquals(expectedInitialReport, actualInitialReport);
        supplyOperation.handle("banana", 50);
        supplyOperation.handle("apple", 30);
        String expectedUpdatedReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,80";
        String actualUpdatedReport = reportCreator.createReport();
        assertEquals(expectedUpdatedReport, actualUpdatedReport);
    }

    @Test
    void handle_supplyNegativeQuantity_notOk() {
        Storage.storage.put("banana", 10);
        assertThrows(InvalidInputDataException.class, () -> supplyOperation.handle("banana", -5),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_supplyZeroQuantity_notOk() {
        Storage.storage.put("banana", 10);
        assertThrows(InvalidInputDataException.class, () -> supplyOperation.handle("banana", 0),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_supplyNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> supplyOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }

    @AfterEach
    void storageClear() {
        Storage.storage.clear();
    }
}
