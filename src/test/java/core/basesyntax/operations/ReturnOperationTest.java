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

class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void handle_returnOperation_Ok() {
        Storage.storage.put("banana", 100);
        Storage.storage.put("apple", 100);
        String actualInitialReport = reportCreator.createReport();
        String expectedInitialReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100";
        assertEquals(expectedInitialReport, actualInitialReport);
        returnOperation.handle("banana", 50);
        returnOperation.handle("apple", 30);
        String expectedUpdatedReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,150"
                + System.lineSeparator()
                + "apple,130";
        String actualUpdatedReport = reportCreator.createReport();
        assertEquals(expectedUpdatedReport, actualUpdatedReport);
    }

    @Test
    void handle_returnNegativeQuantity_notOk() {
        Storage.storage.put("banana", 100);
        assertThrows(InvalidInputDataException.class,
                () -> returnOperation.handle("banana", -5),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_returnZeroQuantity_notOk() {
        Storage.storage.put("banana", 100);
        assertThrows(InvalidInputDataException.class,
                () -> returnOperation.handle("banana", 0),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_returnNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> returnOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }

    @AfterEach
    void storageClean() {
        Storage.storage.clear();
    }
}
