package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class ReportGeneratorImplTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator() +
            "banana,152" + System.lineSeparator() +
            "apple,90" + System.lineSeparator();
    private static final List<FruitTransaction> CORRECT_FRUIT_TRANSACTION_LIST = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
    );
    private static ReportGenerator reportGenerator;
    private static Storage storage;
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(storage),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(storage),
                FruitTransaction.Operation.RETURN, new ReturnOperation(storage),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(storage)
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void getReport_correctReport_ok() {
        shopService.process(CORRECT_FRUIT_TRANSACTION_LIST);
        String actual = reportGenerator.getReport();
        String expected = REPORT;
        Assertions.assertEquals(expected, actual);
    }
}