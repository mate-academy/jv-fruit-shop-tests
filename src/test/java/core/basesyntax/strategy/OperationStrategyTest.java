package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final Map<Operation, OperationHandler> strategyMap = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(storageDao),
            Operation.PURCHASE, new PurchaseOperationHandler(storageDao),
            Operation.RETURN, new ReturnOperationHandler(storageDao),
            Operation.SUPPLY, new SupplyOperationHandler(storageDao)
    );
    private final OperationStrategy operationStrategy
            = new OperationStrategy(strategyMap);

    @Test
    void constructor_strategyMapIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> new OperationStrategy(null));

        assertEquals("Strategy map can`t be null", expected.getMessage());
    }

    @Test
    void getHandlerFor_operationIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> operationStrategy.getHandlerFor(null));

        assertEquals("Operation can`t be null", expected.getMessage());
    }

    @Test
    void getHandlerFor_returnsOperationHandler_ok() {
        assertEquals(BalanceOperationHandler.class,
                operationStrategy.getHandlerFor(Operation.BALANCE).getClass());

        assertEquals(PurchaseOperationHandler.class,
                operationStrategy.getHandlerFor(Operation.PURCHASE).getClass());

        assertEquals(ReturnOperationHandler.class,
                operationStrategy.getHandlerFor(Operation.RETURN).getClass());

        assertEquals(SupplyOperationHandler.class,
                operationStrategy.getHandlerFor(Operation.SUPPLY).getClass());
    }
}
