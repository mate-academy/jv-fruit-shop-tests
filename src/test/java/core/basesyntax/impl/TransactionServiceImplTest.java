package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReadService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.WriteService;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private OperationStrategy operationStrategy;
    private ReadService readService;
    private ParseService parseService;
    private ReportService reportService;
    private WriteService writeService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        readService = new ReadServiceImpl();
        parseService = new ParseServiceImpl();
        reportService = new ReportServiceImpl();
        writeService = new WriteServiceImpl();
        transactionService = new TransactionServiceImpl(operationStrategy, readService,
                parseService, reportService, writeService);
    }

    @Test
    public void testProcessTransactions_MissingOperationStrategy_ExceptionThrown() {
        operationStrategy = null;
        transactionService = new TransactionServiceImpl(operationStrategy, readService,
                parseService, reportService, writeService);
        assertThrows(RuntimeException.class, () -> transactionService.processTransactions());
    }
}
