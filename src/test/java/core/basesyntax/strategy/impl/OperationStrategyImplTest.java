package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.model.FruitsInStore;
import core.basesyntax.model.OperationType;
import core.basesyntax.services.ParametrsValidatorService;
import core.basesyntax.services.impl.ParametrsValidatorServiseImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static List<String> opratorTypeCode;
    private static List<String> fruitsInStore;
    private static ParametrsValidatorService parametrsValidator;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        opratorTypeCode = Arrays.stream(OperationType.values())
                .map(OperationType::getCode)
                .collect(Collectors.toList());
        fruitsInStore = Arrays.stream(FruitsInStore.values())
                .map(FruitsInStore::getCode)
                .collect(Collectors.toList());
        parametrsValidator = new ParametrsValidatorServiseImpl(opratorTypeCode, fruitsInStore);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE.getCode(),new OperationHandlerBalance());
        operationHandlerMap.put(OperationType.SUPPLY.getCode(),new OperationHandlersSupply());
        operationHandlerMap.put(OperationType.RETURN.getCode(),new OperationHandlerReturn());
        operationHandlerMap.put(OperationType.PURCHASE.getCode(),new OperarionHandlerPurchase());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap, parametrsValidator);
    }

    @Test
    public void getOperation_validData_Ok() {
        for (Map.Entry<String, OperationHandler> e : operationHandlerMap.entrySet()) {
            assertEquals(e.getValue(),operationStrategy.getOperation(e.getKey()));
        }
    }

    @Test(expected = InvalidParametersException.class)
    public void getOperation_invalidData_notOk() {
        operationStrategy.getOperation("qw");

    }

    @Test(expected = InvalidParametersException.class)
    public void getOperation_null_notOk() {
        operationStrategy.getOperation(null);
    }
}
