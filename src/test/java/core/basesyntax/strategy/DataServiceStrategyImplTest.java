package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.dataservice.BalanceDataService;
import core.basesyntax.service.dataservice.DataService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataServiceStrategyImplTest {
    private static DataService expectedDataService;
    private static FruitDao fruitDao;
    private static Map<FruitTransaction.Operation, DataService> dataServiceMap;
    private static DataServiceStrategy dataServiceStrategy;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        expectedDataService = new BalanceDataService(fruitDao);
        dataServiceMap = new HashMap<>();
        dataServiceMap.put(FruitTransaction.Operation.BALANCE, expectedDataService);
        dataServiceStrategy = new DataServiceStrategyImpl(dataServiceMap);
    }

    @Test
    void get_correctDataServiceMap_Ok() {
        assertEquals(expectedDataService,
                dataServiceStrategy.get(FruitTransaction.Operation.BALANCE));
    }
}
