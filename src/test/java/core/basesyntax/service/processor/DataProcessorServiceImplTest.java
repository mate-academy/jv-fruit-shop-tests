package core.basesyntax.service.processor;

import static core.basesyntax.database.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.TypeStrategy;
import core.basesyntax.service.strategy.TypeStrategyImpl;
import core.basesyntax.service.strategy.strategyimpl.BalanceStrategy;
import core.basesyntax.service.strategy.strategyimpl.PurchaseStrategy;
import core.basesyntax.service.strategy.strategyimpl.ReturnStrategy;
import core.basesyntax.service.strategy.strategyimpl.SupplyStrategy;
import core.basesyntax.service.strategy.strategyimpl.TypeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessorServiceImplTest {
    private DataProcessorService dataProcessorService;
    private TypeStrategy typeStrategy;

    @BeforeEach
    void setUp() {
        HashMap<FruitRecord.Operation, TypeService> strategyMap = new HashMap<>();
        strategyMap.put(FruitRecord.Operation.BALANCE, new BalanceStrategy());
        strategyMap.put(FruitRecord.Operation.SUPPLY, new SupplyStrategy());
        strategyMap.put(FruitRecord.Operation.PURCHASE, new PurchaseStrategy());
        strategyMap.put(FruitRecord.Operation.RETURN, new ReturnStrategy());
        typeStrategy = new TypeStrategyImpl(strategyMap);
        dataProcessorService = new DataProcessorServiceImpl(typeStrategy);
        storage.clear();
    }

    @Test
    void processData_validInput_Success() {
        List<FruitRecord> fruitRecordList = new ArrayList<>();
        fruitRecordList.add(new FruitRecord(FruitRecord.Operation.BALANCE, "banana", 10));
        fruitRecordList.add(new FruitRecord(FruitRecord.Operation.SUPPLY, "banana", 15));
        fruitRecordList.add(new FruitRecord(FruitRecord.Operation.PURCHASE, "banana", 15));
        fruitRecordList.add(new FruitRecord(FruitRecord.Operation.RETURN, "banana", 10));

        dataProcessorService.processData(fruitRecordList);
        int actualBananaQuantity = storage.get("banana");
        assertEquals(20, actualBananaQuantity);
    }
}
