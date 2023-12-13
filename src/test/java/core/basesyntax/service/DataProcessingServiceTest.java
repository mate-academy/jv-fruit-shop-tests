package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataProcessingServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessingServiceTest {
    private static final List<String> LIST_FROM_FILE =
            List.of("type,fruit,quantity",
                    "b,банан,20",
            "b,яблуко,100",
            "s,банан,100",
            "p,банан,13",
            "r,яблуко,10",
            "p,яблуко,20",
            "p,банан,5",
            "s,банан,50");
    private static final List<String> NOT_VAlID_LIST = List.of("type,fruit,quantity",
             "c,банан,20",
             "q,банан,13");
    private DataProcessingService dataProcessingService;
    private OperationStrategy operationStrategy;
    private FruitDao fruitDao = new FruitDaoImpl();

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler>
                operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        dataProcessingService = new DataProcessingServiceImpl(fruitDao, operationStrategy);
    }

    @Test
    void getFruit_valid_ok() {
        Map<String, Integer> actual = dataProcessingService.getFruit(LIST_FROM_FILE);
        Map<String, Integer> expected = fruitDao.get();
        assertEquals(expected, actual);
    }

    @Test
    void getFruit_notValidLine_notOk() {
        Map<String, Integer> actual = dataProcessingService.getFruit(NOT_VAlID_LIST);
        Map<String, Integer> expected = fruitDao.get();
        assertEquals(expected, actual);
    }
}
