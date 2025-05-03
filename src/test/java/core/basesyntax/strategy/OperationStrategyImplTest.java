package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static FruitDao fruitDao;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        operationHandlerMap = Map.of(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao),
                FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao),
                FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao),
                FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getBalanceOperationHandler_ValidData_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertInstanceOf(BalanceOperationHandler.class, actual);
    }

    @Test
    void getPurchaseOperationHandler_ValidData_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertInstanceOf(PurchaseOperationHandler.class, actual);
    }

    @Test
    void getReturnOperationHandler_ValidData_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertInstanceOf(ReturnOperationHandler.class, actual);
    }

    @Test
    void getSupplyOperationHandler_ValidData_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertInstanceOf(SupplyOperationHandler.class, actual);
    }

}
