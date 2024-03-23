package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.strategy.BalanceOperation;
import core.basesyntax.service.impl.strategy.PurchaseOperation;
import core.basesyntax.service.impl.strategy.ReturnOperation;
import core.basesyntax.service.impl.strategy.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StrategyServiceImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 10;
    private static FruitTransactionDto transactionDto;
    private static StrategyServiceImpl strategyService;
    private static Map<Operation, OperationHandler> operationStrategyMap;

    @BeforeAll
    public static void setUp() {
        transactionDto = new FruitTransactionDto(Operation.BALANCE, FRUIT_NAME, FRUIT_QUANTITY);
        operationStrategyMap = Map.of(
                Operation.BALANCE, new BalanceOperation(),
                Operation.RETURN, new ReturnOperation(),
                Operation.PURCHASE, new PurchaseOperation(),
                Operation.SUPPLY, new SupplyOperation()
        );
        strategyService = new StrategyServiceImpl(operationStrategyMap);
    }

    @Test
    void getHandler_getBalanceOperation_Ok() {
        var expected = operationStrategyMap.get(Operation.BALANCE);
        var actual = strategyService.getHandler(transactionDto);

        assertEquals(expected, actual);
    }

    @Test
    void processData_BalanceOperationHandled_Ok() {
        Storage.fruitStorage.clear();
        strategyService.processData(List.of(transactionDto), operationStrategyMap);
        int actual = Storage.fruitStorage.get(FRUIT_NAME);
        assertEquals(1, Storage.fruitStorage.size());
        assertEquals(FRUIT_QUANTITY, actual);
    }
}
