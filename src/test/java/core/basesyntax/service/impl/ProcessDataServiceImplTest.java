package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceHandler;
import core.basesyntax.service.strategy.impl.HandlerStrategy;
import core.basesyntax.service.strategy.impl.PurchaseHandler;
import core.basesyntax.service.strategy.impl.ReturnHandler;
import core.basesyntax.service.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessDataServiceImplTest {

    private static ProcessDataServiceImpl processDataService;

    private static StorageDao storageDao;
    private static HandlerStrategy handlerStrategy;

    private static ConvertDataServiceImpl convertDataService;

    private static Map<Operation, OperationHandler> strategyMap;

    @BeforeAll
    static void beforeAll() {
        strategyMap = new HashMap<>();
        storageDao = new StorageDaoImpl();
        handlerStrategy = new HandlerStrategy(strategyMap);
        processDataService = new ProcessDataServiceImpl(storageDao, handlerStrategy);
        convertDataService = new ConvertDataServiceImpl();
    }

    @BeforeEach
    void setUp() {
        strategyMap.put(Operation.BALANCE, new BalanceHandler(storageDao));
        strategyMap.put(Operation.PURCHASE, new PurchaseHandler(storageDao));
        strategyMap.put(Operation.SUPPLY, new SupplyHandler(storageDao));
        strategyMap.put(Operation.RETURN, new ReturnHandler(storageDao));
    }

    @Test
    void processing_returnMap_Ok() {
        List<String> list = List.of("b,banana,20", "b,apple,100", "r,apple,10");
        List<FruitTransaction> listFruit = convertDataService.processingData(list);

        Map<String, Integer> map = processDataService.processing(listFruit);
        int sizeExpect = 2;
        Assertions.assertEquals(sizeExpect, map.size());
    }

    @Test
    void processing_BalanceNegative_NotOk() {
        List<String> list = List.of("b,banana,20", "b,apple,100", "p,apple,1500");
        List<FruitTransaction> listFruit = convertDataService.processingData(list);

        Map<String, Integer> map = processDataService.processing(listFruit);
        int sizeExpect = 0;
        Assertions.assertEquals(sizeExpect, map.get("apple"));

    }
}
