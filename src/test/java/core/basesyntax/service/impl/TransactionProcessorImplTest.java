package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import core.basesyntax.service.StrategyOperationService;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private StrategyOperationService operationService;
    private Map<OperationType, OperationHandler> operationHandlerMap;
    private ShopTransaction transaction1;
    private ShopTransaction transaction2;
    private List<ShopTransaction> transactionsList;
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        transaction1 = new ShopTransaction(OperationType.PURCHASE, "banana", 10);
        transaction2 = new ShopTransaction(OperationType.RETURN, "orange", 10);
        transactionsList = List.of(transaction1, transaction2);
        operationService = new StrategyOperationServiceImpl(operationHandlerMap);
        transactionProcessor = new TransactionProcessorImpl(operationService);
    }

    @Test
    void emptyListTransactions_NotOk() {
        List<ShopTransaction> list = new ArrayList<>();
        int expected = list.size();
        assertEquals(0, expected);
    }

    @Test
    void nullList_NotOk() {
        List<ShopTransaction> list = null;
        assertNull(list);
    }

    @Test
    void nullTransaction_NotOk() {
        assertThrows(NullPointerException.class, () ->
                transactionProcessor.process(transactionsList),
                "handle(transaction) is null");
    }
}
