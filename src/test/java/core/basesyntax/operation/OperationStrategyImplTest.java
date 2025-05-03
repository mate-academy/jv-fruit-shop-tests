package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitQuantityDao;
import core.basesyntax.dao.FruitQuantityDaoImpl;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    void setUp() {
        FruitQuantityDao fruitQuantityDao = new FruitQuantityDaoImpl();
        operationHandlerMap = Map.of(
                Operation.BALANCE, new BalanceOperationHandlerImpl(fruitQuantityDao),
                Operation.SUPPLY, new SupplyOperationHandlerImpl(fruitQuantityDao),
                Operation.PURCHASE, new PurchaseOperationHandlerImpl(fruitQuantityDao),
                Operation.RETURN, new ReturnOperationHandlerImpl(fruitQuantityDao)
        );
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_balanceOperationCode_ok() {
        OperationHandler handler = operationStrategy
                .get(Operation.fromString("b"));
        assertEquals(BalanceOperationHandlerImpl.class, handler.getClass());
    }

    @Test
    void get_supplyOperationCode_ok() {
        OperationHandler handler = operationStrategy
                .get(Operation.fromString("s"));
        assertEquals(SupplyOperationHandlerImpl.class, handler.getClass());
    }

    @Test
    void get_purchaseOperationCode_ok() {
        OperationHandler handler = operationStrategy
                .get(Operation.fromString("p"));
        assertEquals(PurchaseOperationHandlerImpl.class, handler.getClass());
    }

    @Test
    void get_returnOperationCode_ok() {
        OperationHandler handler = operationStrategy
                .get(Operation.fromString("r"));
        assertEquals(ReturnOperationHandlerImpl.class, handler.getClass());
    }
}
