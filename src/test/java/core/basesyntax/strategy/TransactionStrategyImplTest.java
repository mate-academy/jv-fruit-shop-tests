package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandlerImpl;
import core.basesyntax.service.operation.ReturnHandlerImpl;
import core.basesyntax.service.operation.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyImplTest {
    private static final Map<FruitTransaction.Operation,
            OperationHandler> handlersMap = new HashMap<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fillHandlersMap();
    }

    @Test
    public void getOperationHandler_balanceOperation_Ok() {
        OperationHandler expectedOperationHandler = new BalanceHandlerImpl();
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("b"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test
    public void getOperationHandler_purchaseOperation_Ok() {
        OperationHandler expectedOperationHandler = new PurchaseHandlerImpl();
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("p"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test
    public void getOperationHandler_returnOperation_Ok() {
        OperationHandler expectedOperationHandler = new ReturnHandlerImpl();
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("r"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test
    public void getOperationHandler_supplyOperation_Ok() {
        OperationHandler expectedOperationHandler = new SupplyHandlerImpl();
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        OperationHandler actualOperationHandler = transactionStrategy
                .getOperationHandler(FruitTransaction.Operation.getByCode("s"));
        assertEquals(expectedOperationHandler.getClass(), actualOperationHandler.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void getOperationHandler_nullHandlersMap_notOk() {
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(null);
        transactionStrategy.getOperationHandler(FruitTransaction.Operation.getByCode("b"));
    }

    private static void fillHandlersMap() {
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
    }
}
