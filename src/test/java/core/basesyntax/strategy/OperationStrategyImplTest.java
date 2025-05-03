package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationToHandler = Map
                .of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationToHandler);
    }

    @Test
    void getHandler_CorrectHandler_Ok() {
        OperationHandler balance = operationStrategy.getHandler(FruitTransaction
                .Operation.BALANCE);
        OperationHandler purchase = operationStrategy.getHandler(FruitTransaction
                .Operation.PURCHASE);
        assertInstanceOf(BalanceOperationHandler.class, balance);
        assertInstanceOf(PurchaseOperationHandler.class,purchase);
    }
}
