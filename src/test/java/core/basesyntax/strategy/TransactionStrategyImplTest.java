package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandlerImpl;
import core.basesyntax.service.operation.ReturnHandlerImpl;
import core.basesyntax.service.operation.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TransactionStrategyImplTest {
    private static TransactionStrategy transactionStrategy;
    private static final Map<FruitTransaction.Operation,
            OperationHandler> handlersMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
    }

    @Test
    public void getOperationHandler_balanceOperation_Ok() {
        OperationHandler expectedOperationHandler = new BalanceHandlerImpl();
        transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("b"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test
    public void getOperationHandler_purchaseOperation_Ok() {
        OperationHandler expectedOperationHandler = new PurchaseHandlerImpl();
        transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("p"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test
    public void getOperationHandler_returnOperation_Ok() {
        OperationHandler expectedOperationHandler = new ReturnHandlerImpl();
        transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("r"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test
    public void getOperationHandler_supplyOperation_Ok() {
        OperationHandler expectedOperationHandler = new SupplyHandlerImpl();
        transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("s"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test
    public void getOperationHandler_nullHandlersMap_notOk() {
        transactionStrategy = new TransactionStrategyImpl(null);
        assertThrows(RuntimeException.class, () -> transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("b")));
    }
}
