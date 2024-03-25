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
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static final Map<Operation, OperationHandler> STRATEGY_MAP = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(STORAGE_DAO),
            Operation.PURCHASE, new PurchaseOperationHandler(STORAGE_DAO),
            Operation.RETURN, new ReturnOperationHandler(STORAGE_DAO),
            Operation.SUPPLY, new SupplyOperationHandler(STORAGE_DAO)
    );
    private static final OperationStrategy OPERATION_STRATEGY
            = new OperationStrategy(STRATEGY_MAP);

    @Test
    void constructor_strategyMapIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> new OperationStrategy(null));

        assertEquals("Strategy map can`t be null", expected.getMessage());
    }

    @Test
    void getHandlerFor_operationIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> OPERATION_STRATEGY.getHandlerFor(null));

        assertEquals("Operation can`t be null", expected.getMessage());
    }

    @Test
    void getHandlerFor_returnsOperationHandler_ok() {
        assertEquals(BalanceOperationHandler.class,
                OPERATION_STRATEGY.getHandlerFor(Operation.BALANCE).getClass());

        assertEquals(PurchaseOperationHandler.class,
                OPERATION_STRATEGY.getHandlerFor(Operation.PURCHASE).getClass());

        assertEquals(ReturnOperationHandler.class,
                OPERATION_STRATEGY.getHandlerFor(Operation.RETURN).getClass());

        assertEquals(SupplyOperationHandler.class,
                OPERATION_STRATEGY.getHandlerFor(Operation.SUPPLY).getClass());
    }
}
