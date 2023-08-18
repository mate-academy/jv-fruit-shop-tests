package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.exception.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;
    private OperationHandler operationHandler;
    private ReportCreator reportCreator;
    private FruitTransaction inValidFruitTransaction;

    @BeforeEach
    void setUp() {
        fruitShopService = new FruitShopServiceImpl();
        operationHandler = new BalanceOperation();
        reportCreator = new ReportCreatorImpl();
        Storage.storage.clear();
    }

    @Test
    void process_validParameters_Ok() {
        FruitTransaction validFruitTransaction
                = new FruitTransaction(Operation.BALANCE, "banana", 20);
        fruitShopService.process(validFruitTransaction, operationHandler);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,20";
        String result = reportCreator.createReport();
        assertEquals(expected, result);
    }

    @Test
    void process_inValidParameters_notOk() {
        inValidFruitTransaction = null;
        assertThrows(InvalidDataException.class,
                () -> fruitShopService.process(inValidFruitTransaction, operationHandler),
                "InvalidDataException expected to be thrown");
    }
}
