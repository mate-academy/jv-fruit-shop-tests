package core.basesyntax.fileprocessing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.handlers.OperationHandler;
import core.basesyntax.services.handlers.impl.BalanceOperationHandler;
import core.basesyntax.services.handlers.impl.PurchaseOperationHandler;
import core.basesyntax.services.handlers.impl.ReturnOperationHandler;
import core.basesyntax.services.handlers.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
    private static OperationStrategy operationStrategyImpl;

    @BeforeAll
    static void initOperationStrategy() {
        operationStrategyImpl = new OperationStrategyImpl(operationHandlerMap);
    }

    @AfterAll
    static void closeStrategy() {
        operationStrategyImpl = null;
    }

    @Test
    void get_badRequests_Ok() {
        assertFalse(operationStrategyImpl.get(FruitTransaction.Operation.BALANCE) instanceof PurchaseOperationHandler);
        assertFalse(operationStrategyImpl.get(FruitTransaction.Operation.PURCHASE) instanceof ReturnOperationHandler);
        assertFalse(operationStrategyImpl.get(FruitTransaction.Operation.RETURN) instanceof SupplyOperationHandler);
        assertFalse(operationStrategyImpl.get(FruitTransaction.Operation.SUPPLY) instanceof BalanceOperationHandler);
    }
    @Test
    void get_normalRequests_Ok() {
        assertInstanceOf(BalanceOperationHandler.class, operationStrategyImpl.get(FruitTransaction.Operation.BALANCE));
        assertInstanceOf(PurchaseOperationHandler.class, operationStrategyImpl.get(FruitTransaction.Operation.PURCHASE));
        assertInstanceOf(ReturnOperationHandler.class, operationStrategyImpl.get(FruitTransaction.Operation.RETURN));
        assertInstanceOf(SupplyOperationHandler.class, operationStrategyImpl.get(FruitTransaction.Operation.SUPPLY));
    }
}
