package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    private static OperationStrategy strategy;

    @BeforeAll
    static void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(fruitDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(fruitDao)
        );

        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_validData_ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.BALANCE);

        assertEquals(BalanceOperationHandler.class, actual.getClass(),
                "Returned handler must be the same as expected!");
    }

    @Test
    void get_nullData_throwException() {
        assertThrows(NullPointerException.class, () -> strategy.get(null));
    }

    @Test
    void get_notExistData_returnNull() {
        assertNull(strategy.get(FruitTransaction.Operation.RETURN));
    }

}
