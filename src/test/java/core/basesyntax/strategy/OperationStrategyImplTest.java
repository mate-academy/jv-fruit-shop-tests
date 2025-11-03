package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Type,
            OperationHandler> operationHandlerMap = new HashMap<>();
    private static BalanceOperationHandler balanceOperationHandler;
    private static SupplyOperationHandler supplyOperationHandler;
    private static ReturnOperationHandler returnOperationHandler;
    private static PurchaseOperationHandler purchaseOperationHandler;

    private static OperationStrategy operationStrategy;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();

        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
        supplyOperationHandler = new SupplyOperationHandler(fruitDao);
        returnOperationHandler = new ReturnOperationHandler(fruitDao);
        purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);

        operationHandlerMap.put(FruitTransaction.Type.BALANCE,
                balanceOperationHandler);
        operationHandlerMap.put(FruitTransaction.Type.SUPPLY,
                supplyOperationHandler);
        operationHandlerMap.put(FruitTransaction.Type.RETURN,
                returnOperationHandler);
        operationHandlerMap.put(FruitTransaction.Type.PURCHASE,
                purchaseOperationHandler);

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_getAlreadyExistHandler_isOk() {
        OperationHandler actualBalance = operationStrategy.get(FruitTransaction.Type.BALANCE);
        assertSame(balanceOperationHandler, actualBalance);

        OperationHandler actualSupply = operationStrategy.get(FruitTransaction.Type.SUPPLY);
        assertSame(supplyOperationHandler, actualSupply);

        OperationHandler actualReturn = operationStrategy.get(FruitTransaction.Type.RETURN);
        assertSame(returnOperationHandler, actualReturn);

        OperationHandler actualPurchase = operationStrategy.get(FruitTransaction.Type.PURCHASE);
        assertSame(purchaseOperationHandler, actualPurchase);
    }

    @Test
    void get_notExistHandler_throwException() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.get(FruitTransaction.Type.valueOf("UNKNOWN")));
        assertThrows(RuntimeException.class,
                () -> operationStrategy.get(null));
    }
}
