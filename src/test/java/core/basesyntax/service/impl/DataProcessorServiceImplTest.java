package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.BuyOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataProcessorServiceImplTest {
    private static OperationStrategy operationStrategy;
    private static DataProcessorService dataProcessorService;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.PURCHASE, new BuyOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler()));
        dataProcessorService = new DataProcessorServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void updateDataInStorage_invalidOperation_notOk() {
        List<String> data = List.of("b,banana,1", "b,apple,1", "q,banana,1");
        assertThrows(RuntimeException.class,
                () -> dataProcessorService.updateDataInStorage(data));
    }

    @Test
    void updateDataInStorage_negativeQuantity_notOk() {
        List<String> data = List.of("b,banana,-1", "b,apple,1", "s,banana,1");
        assertThrows(RuntimeException.class,
                () -> dataProcessorService.updateDataInStorage(data));
    }

    @Test
    void update_ok() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,1");
        data.add("b,apple,1");
        data.add("p,banana,1");
        dataProcessorService.updateDataInStorage(data);
        assertEquals(Storage.STORAGE.get("banana"), 0);
        assertEquals(Storage.STORAGE.get("apple"), 1);
    }
}
