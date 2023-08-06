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

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void handle_purchaseOperation_Ok() {
        Storage.storage.put("banana", 30);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10";
        purchaseOperation.handle("banana", 20);
        String result = reportCreator.createReport();
        assertEquals(expected, result);
    }

    @Test
    void handle_purchaseOperation_notOk() {
        Storage.storage.put("banana", 30);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle("banana", 40),
                "InvalidInputDataException expected to be thrown");
    }

    @Test
    void handle_purchaseNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> purchaseOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }

    @Test
    void handle_purchaseNegativeInputFruitQuantity_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> purchaseOperation.handle("banana", -5),
                "InvalidInputDataException expected to be thrown");
    }

    @AfterEach
    void storageClean() {
        Storage.storage.clear();
    }
}
