package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import data.base.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.DataProcessorService;
import service.OperationStrategy;
import service.impl.DataProcessorServiceImpl;
import service.impl.OperationStrategyImpl;
import service.strategy.hendlers.BalanceOperationHandler;
import service.strategy.hendlers.PurchaseOperationHandler;
import service.strategy.hendlers.ReturnOperationHandler;
import service.strategy.hendlers.SupplyOperationHandler;

public class DataProcessorServiceImplTest {
    private static DataProcessorService dataProcessorService;

    @BeforeAll
    static void beforeAll() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler()));
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
                () -> dataProcessorService.processData(data));
    }

    @Test
    void negativeQuantity_notOk() {
        List<String> data = List.of("b,banana,-1", "b,apple,1", "s,banana,1");
        assertThrows(RuntimeException.class,
                () -> dataProcessorService.processData(data));
    }

    @Test
    void update_ok() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,1");
        data.add("b,apple,1");
        data.add("p,banana,1");
        dataProcessorService.processData(data);
        assertEquals(Storage.STORAGE.get("banana"), 0);
        assertEquals(Storage.STORAGE.get("apple"), 1);
    }
}


