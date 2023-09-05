package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler>
            OPERATION_HANDLER_MAP = new HashMap<>();
    private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private final TransactionStrategy transactionStrategy
            = new TransactionStrategyImpl(OPERATION_HANDLER_MAP);

    @BeforeAll
    public static void init() {
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(FRUIT_DAO));
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(FRUIT_DAO));
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(FRUIT_DAO));
        OPERATION_HANDLER_MAP.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(FRUIT_DAO));
    }

    @Test
    void getOperation_ExistedOperation_Ok() {
        for (Map.Entry<FruitTransaction.Operation, OperationHandler> entry
                : OPERATION_HANDLER_MAP.entrySet()) {
            OperationHandler operation = transactionStrategy.getOperation(entry.getKey());
            System.out.println(operation);
            assertEquals(entry.getValue(), operation);
        }
    }

    @Test
    void getOperation_notExistedOperation_notOk() {
        OperationHandler actual = transactionStrategy.getOperation(null);
        assertNull(actual);
    }
}
