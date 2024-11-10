package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import core.basesyntax.service.StrategyOperationService;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import exception.OperationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private OperationType operationType;
    private StrategyOperationService operationService;
    private Map<OperationType, OperationHandler> operationHandlerMap;
    private ShopTransaction firstTransaction;
    private ShopTransaction secondTransaction;
    private List<ShopTransaction> transactionsList;
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        firstTransaction = new ShopTransaction(OperationType.PURCHASE, "banana", 10);
        secondTransaction = new ShopTransaction(OperationType.RETURN, "orange", 10);
        transactionsList = List.of(firstTransaction, secondTransaction);
        operationService = new StrategyOperationServiceImpl(operationHandlerMap);
        transactionProcessor = new TransactionProcessorImpl(operationService);
    }

    @Test
    void getOperationTypeReturnCorrectOperation_Ok() {
        assertEquals(OperationType.BALANCE, OperationType.getOperationType("b"));
        assertEquals(OperationType.SUPPLY, OperationType.getOperationType("s"));
        assertEquals(OperationType.PURCHASE, OperationType.getOperationType("p"));
        assertEquals(OperationType.RETURN, OperationType.getOperationType("r"));
    }

    @Test
    void getCodeOperationReturnCorrectCode_Ok() {
        assertEquals("b", OperationType.BALANCE.getCodeOperation());
        assertEquals("s", OperationType.SUPPLY.getCodeOperation());
        assertEquals("p", OperationType.PURCHASE.getCodeOperation());
        assertEquals("r", OperationType.RETURN.getCodeOperation());
    }

    @Test
    void invalidOperationType_NotOk() {
        assertThrows(OperationException.class,
                () -> OperationType.getOperationType("f"),
                "Not correct operation code: f");
    }

    @Test
    void nullTransaction_NotOk() {
        assertThrows(NullPointerException.class, () ->
                transactionProcessor.process(transactionsList),
                "handle(transaction) is null");
    }
}
