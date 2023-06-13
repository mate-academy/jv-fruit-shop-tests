package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final int VALID_INTEGER_VALUE = 10;
    private static final String FIRST_LINE_IN_OUTPUT = "fruit,quantity" + System.lineSeparator();
    private static final String VAlID_STRING_VALUE = "strawberry";
    private static final String SEPARATOR = ",";
    private ReportService reportService;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private Map<String, Integer> transactionsMap;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        reportService = new ReportServiceImpl(operationStrategy);
        transactionsMap = new HashMap<>();
        fruitTransactions = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void createFinalReport_MapKeyIsNull_NotOk() {
        transactionsMap.put(null, VALID_INTEGER_VALUE);
        Assert.assertThrows(NullPointerException.class, () ->
                reportService.createFinalReport(transactionsMap));
    }

    @Test
    void createFinalReport_MapValueIsNull_NotOk() {
        transactionsMap.put(VAlID_STRING_VALUE, null);
        Assert.assertThrows(NullPointerException.class, () ->
                reportService.createFinalReport(transactionsMap));
    }

    @Test
    void createFinalReport_ValidKeyAndValue_Ok() {
        StringBuilder builder = new StringBuilder(FIRST_LINE_IN_OUTPUT);
        String expectedString = builder.append(VAlID_STRING_VALUE)
                .append(SEPARATOR)
                .append(VALID_INTEGER_VALUE)
                .append(System.lineSeparator())
                .toString();
        transactionsMap.put(VAlID_STRING_VALUE, VALID_INTEGER_VALUE);
        Assert.assertEquals(expectedString, reportService.createFinalReport(transactionsMap));
    }

    @Test
    void applyTransactionsToStorage_ValidInput_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        fruitTransactions.add(new FruitTransaction(
                VAlID_STRING_VALUE, FruitTransaction.Operation.BALANCE, VALID_INTEGER_VALUE));
        fruitTransactions.add(new FruitTransaction(
                VAlID_STRING_VALUE, FruitTransaction.Operation.SUPPLY, VALID_INTEGER_VALUE));
        reportService.applyTransactionsToStorage(fruitTransactions);
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put(VAlID_STRING_VALUE, VALID_INTEGER_VALUE + VALID_INTEGER_VALUE);
        Assert.assertEquals(expectedMap, Storage.fruits);
    }
}
