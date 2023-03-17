package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.StrategyApplicationService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.strategy.handlers.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrategyApplicationServiceImplTest {
    private static final List<String[]> VALID_LIST = Arrays.asList(
            new String[]{"b", "banana", "20"},
            new String[]{"s", "banana", "100"},
            new String[]{"p", "banana", "20"},
            new String[]{"r", "banana", "100"});
    private static final List<String[]> INVALID_OPERATION_LIST = Arrays.asList(
            new String[]{"bb", "banana", "20"},
            new String[]{"ss", "banana", "100"},
            new String[]{"pp", "banana", "20"},
            new String[]{"rr", "banana", "100"});
    private List<String[]> emptyList;
    private OperationStrategy operationStrategy;
    private StrategyApplicationService strategyApplicationService;

    @Before
    public void setUp() {
        emptyList = new ArrayList<>();
        operationStrategy = new OperationStrategyImpl(Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler()
        ));
        strategyApplicationService = new StrategyApplicationServiceImpl(operationStrategy);
    }

    @Test(expected = RuntimeException.class)
    public void applyOperationStrategy_invalidOperation_notOk() {
        strategyApplicationService.applyOperationStrategy(INVALID_OPERATION_LIST);
        fail("Should throw RuntimeException when we try to apply "
                + "Operation Strategy with invalid operator");
    }

    @Test
    public void applyOperationStrategy_validData_Ok() {
        strategyApplicationService.applyOperationStrategy(VALID_LIST);
        Assert.assertEquals((Integer) 200, Storage.get("banana"));
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void applyOperationStrategy_nullData_notOk() {
        strategyApplicationService.applyOperationStrategy(null);
        fail("Should throw RuntimeException when we try to apply "
                + "Operation Strategy with null data");
    }

    @Test(expected = RuntimeException.class)
    public void applyOperationStrategy_emptyList_notOk() {
        strategyApplicationService.applyOperationStrategy(emptyList);
        fail("Should throw RuntimeException when we try to apply "
                + "Operation Strategy with empty list");
    }
}
