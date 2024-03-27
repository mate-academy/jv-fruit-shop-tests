package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.strategyimpl.BalanceOperation;
import core.basesyntax.service.strategy.strategyimpl.PurchaseOperation;
import core.basesyntax.service.strategy.strategyimpl.ReturnOperation;
import core.basesyntax.service.strategy.strategyimpl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStrategyTest {
    private static final Operation OPERATION_CODE_BALANCE = Operation.BALANCE;
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 50;

    private FruitStrategy operationStrategy;
    private Map<Operation, OperationHandler> operations;
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        operations = new HashMap<>();
        operations.put(Operation.BALANCE, new BalanceOperation(storageDao));
        operations.put(Operation.PURCHASE, new PurchaseOperation(storageDao));
        operations.put(Operation.RETURN, new ReturnOperation(storageDao));
        operations.put(Operation.SUPPLY, new SupplyOperation(storageDao));

        operationStrategy = new FruitStrategy(operations);
    }

    @Test
    public void getHandler_inputBalanceOperation_Ok() {
        FruitTransactionDto transaction = new FruitTransactionDto(OPERATION_CODE_BALANCE,
                FRUIT_NAME,
                QUANTITY);
        var expected = operations.get(OPERATION_CODE_BALANCE);
        var actual = operationStrategy.findHandlerFor(transaction);
        assertEquals(expected, actual);
    }
}
