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
    void operationType_Ok() {
        final String actual = transactionsList.get(0).getType().getCodeOperation();
        final String actual2 = transactionsList.get(1).getType().getCodeOperation();
        assertEquals("p", actual);
        assertEquals("r", actual2);
    }

    @Test
    void fruitName_Ok() {
        final String actualFruitName1 = transactionsList.get(0).getFruitName();
        final String actualFruitName2 = transactionsList.get(1).getFruitName();
        assertEquals("banana", actualFruitName1);
        assertEquals("orange", actualFruitName2);
    }

    @Test
    void nullTransaction_NotOk() {
        assertThrows(NullPointerException.class, () ->
                transactionProcessor.process(transactionsList),
                "handle(transaction) is null");
    }
}
