package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.impl.BalanceOperationHandler;
import core.basesyntax.service.operations.impl.PurchaseOperationHandler;
import core.basesyntax.service.operations.impl.ReturnOperationHandler;
import core.basesyntax.service.operations.impl.SupplyOperationHandler;
import core.basesyntax.service.strategy.Operation;
import exception.CustomException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private Storage storage;
    private OperationStrategyImpl operationStrategy;
    private Map<Operation, OperationHandler> strategyMap;
    private FruitTransactionDto fruitTransactionDto;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        strategyMap = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(storage),
                Operation.PURCHASE, new PurchaseOperationHandler(storage),
                Operation.RETURN, new ReturnOperationHandler(storage),
                Operation.SUPPLY, new SupplyOperationHandler(storage));
        operationStrategy = new OperationStrategyImpl(strategyMap);
    }

    @Test
    public void get_NullOperation_noOk() {
        fruitTransactionDto = new FruitTransactionDto(null, "apple", 5);
        CustomException customException = assertThrows(CustomException.class,
                () -> operationStrategy.get(fruitTransactionDto));
        assertEquals("Operation cannot be null", customException.getMessage());
    }
}
