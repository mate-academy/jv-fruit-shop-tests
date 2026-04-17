package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategyImpl;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private BalanceOperation balanceHandler;
    private PurchaseOperation purchaseHandler;
    private SupplyOperation supplyHandler;
    private ReturnOperation returnHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperation();
        purchaseHandler = new PurchaseOperation();
        supplyHandler = new SupplyOperation();
        returnHandler = new ReturnOperation();

        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(FruitTransaction.Operation.RETURN, returnHandler);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);

        operationStrategyImpl = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void operationStrategyImplBuilder_nullOperationHandlerMap_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategyImpl = new OperationStrategyImpl(null));
    }

    @Test
    void getOperation_nullOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategyImpl.getOperationHandler(null));
    }

    @Test
    void getOperation_returnBalanceOperation_ok() {
        OperationHandler actual = operationStrategyImpl
                .getOperationHandler(FruitTransaction.Operation.BALANCE);
        assertSame(balanceHandler, actual);
    }

    @Test
    void getOperation_returnPurchaseOperation_ok() {
        OperationHandler actual = operationStrategyImpl
                .getOperationHandler(FruitTransaction.Operation.PURCHASE);
        assertSame(purchaseHandler, actual);
    }

    @Test
    void getOperation_returnReturnOperation_ok() {
        OperationHandler actual = operationStrategyImpl
                .getOperationHandler(FruitTransaction.Operation.RETURN);
        assertSame(returnHandler, actual);
    }

    @Test
    void getOperation_returnSupplyOperation_ok() {
        OperationHandler actual = operationStrategyImpl
                .getOperationHandler(FruitTransaction.Operation.SUPPLY);
        assertSame(supplyHandler, actual);
    }

    @Test
    void getOperation_emptyOperationMap_NotOk() {
        operationStrategyImpl = new OperationStrategyImpl(new HashMap<>());
        assertThrows(RuntimeException.class,
                () -> operationStrategyImpl
                        .getOperationHandler(FruitTransaction.Operation.BALANCE));
    }

}
