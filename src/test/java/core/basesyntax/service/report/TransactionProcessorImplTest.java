package core.basesyntax.service.report;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.service.counter.BalanceHandlerImpl;
import core.basesyntax.service.counter.OperationHandler;
import core.basesyntax.service.counter.PurchaseHandlerImpl;
import core.basesyntax.service.counter.ReturnHandlerImpl;
import core.basesyntax.service.counter.SupplyHandlerImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.transaction.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static final int EXPECTED_QUANTITY = 18;
    private Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap
            = new HashMap<>();
    private List<FruitTransaction> fruitTransactionList = new ArrayList<>();
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
    private TransactionProcessor transactionProcessor
            = new TransactionProcessorImpl(operationStrategy);

    @BeforeEach
    void setUp() {
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        operationStrategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        operationStrategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        operationStrategyMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
    }

    @Test
    void process_correctData_Ok() {
        FruitTransaction firstFruitTransaction = new FruitTransaction();
        firstFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstFruitTransaction.setFruit("banana");
        firstFruitTransaction.setQuantity(30);
        fruitTransactionList.add(firstFruitTransaction);

        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        secondFruitTransaction.setFruit("banana");
        secondFruitTransaction.setQuantity(12);
        fruitTransactionList.add(secondFruitTransaction);

        transactionProcessor.process(fruitTransactionList);

        assertEquals(EXPECTED_QUANTITY, Storage.getFruitTypesAndQuantity().get("banana"));
    }

    @Test
    void process_null_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionProcessor.process(null);
        });
    }
}
