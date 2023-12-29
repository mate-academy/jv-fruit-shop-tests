package core.basesyntax.strategy;

import core.basesyntax.database.FruitStorage;
import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseHandler;
import core.basesyntax.handlers.QuantityAdditionHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final Map<String, OperationHandler> operationHandlerMap = Map.of(
            OperationStrategyImpl.Operation.BALANCE.getOperationCode(), new BalanceHandler(),
            OperationStrategyImpl.Operation.PURCHASE.getOperationCode(), new PurchaseHandler(),
            OperationStrategyImpl.Operation.RETURN.getOperationCode(),
            new QuantityAdditionHandler(),
            OperationStrategyImpl.Operation.SUPPLY.getOperationCode(),
            new QuantityAdditionHandler());
    private static final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlerMap);

    @AfterEach
    void tearDown() {
        FruitStorage.storageData.clear();
    }

    @Test
    void operationStrategy_validData_Ok() {
        List<String> inputList = List.of("b,banana,20", "p,banana,10", "s,banana,15", "r,banana,5");
        operationStrategy.operationPerform(inputList);
        Assertions.assertEquals(30, FruitStorage.storageData.get("banana"));
        List<String> inputListWithNullQuantities
                = List.of("b,banana,0", "p,banana,0", "s,banana,0", "r,banana,0");
        operationStrategy.operationPerform(inputListWithNullQuantities);
        Assertions.assertEquals(0, FruitStorage.storageData.get("banana"));
    }

    @Test
    void operationStrategy_wrongDataFormat_notOk() {
        List<String> inputListWithWrongOperationCode = List.of("invalidOperationCode,banana,20");
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.operationPerform(inputListWithWrongOperationCode));
        List<String> inputListWithNoOperationCode = List.of("banana,20");
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.operationPerform(inputListWithNoOperationCode));
        List<String> inputListWithWrongQuantityFormat = List.of("b,banana,s");
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.operationPerform(inputListWithWrongQuantityFormat));
    }
}
