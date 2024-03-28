package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.impl.BalanceOperationHandler;
import core.basesyntax.service.operations.impl.PurchaseOperationHandler;
import core.basesyntax.service.operations.impl.ReturnOperationHandler;
import core.basesyntax.service.operations.impl.SupplyOperationHandler;
import core.basesyntax.service.strategy.Operation;
import core.basesyntax.service.strategy.OperationStrategy;
import exception.CustomException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private TransactionProcessorImpl transactionProcessor;
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        Map<Operation, OperationHandler> strategyMap = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(new Storage()),
                Operation.PURCHASE, new PurchaseOperationHandler(new Storage()),
                Operation.RETURN, new ReturnOperationHandler(new Storage()),
                Operation.SUPPLY, new SupplyOperationHandler(new Storage()));
        operationStrategy = new OperationStrategyImpl(strategyMap);
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
    }

    @Test
    public void process_NullList_notOk() {
        CustomException customException = assertThrows(CustomException.class,
                () -> transactionProcessor.process(null));
        assertEquals("Transaction list is null", customException.getMessage());
    }
}
