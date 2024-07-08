package core.basesyntax.service.processor;

import static core.basesyntax.database.Storage.storage;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.TypeStrategy;
import core.basesyntax.service.strategy.TypeStrategyImpl;
import core.basesyntax.service.strategy.strategyimpl.BalanceStrategy;
import core.basesyntax.service.strategy.strategyimpl.PurchaseStrategy;
import core.basesyntax.service.strategy.strategyimpl.ReturnStrategy;
import core.basesyntax.service.strategy.strategyimpl.SupplyStrategy;
import core.basesyntax.service.strategy.strategyimpl.TypeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessorServiceImplTest {
    private DataProcessorService dataProcessorService;
    private TypeStrategy typeStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitRecord.Operation, TypeService> strategyMap = Map.of(
                FruitRecord.Operation.BALANCE, new BalanceStrategy(),
                FruitRecord.Operation.SUPPLY, new SupplyStrategy(),
                FruitRecord.Operation.PURCHASE, new PurchaseStrategy(),
                FruitRecord.Operation.RETURN, new ReturnStrategy()
        );
        typeStrategy = new TypeStrategyImpl(strategyMap);
        dataProcessorService = new DataProcessorServiceImpl(typeStrategy);
        storage.clear();
    }

    @AfterAll
    static void afterAll() {
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

    @Test
    void processData_negativeResult_Failure() {
        List<FruitRecord> fruitRecords = new ArrayList<>();
        fruitRecords.add(new FruitRecord(FruitRecord.Operation.BALANCE, "apple", 10));
        fruitRecords.add(new FruitRecord(FruitRecord.Operation.SUPPLY, "apple", 11));
        fruitRecords.add(new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 50));
        fruitRecords.add(new FruitRecord(FruitRecord.Operation.RETURN, "apple", 9));

        dataProcessorService.processData(fruitRecords);
        int result = storage.get("apple");
        assertTrue(result < 0, "The result should be negative");
        int expectedNegativeValue = - 20;

        assertEquals(expectedNegativeValue, result);
    }

    @Test
    void processData_nullValue_Exception() {
        List<FruitRecord> nullList = null;
        assertThrows(NullPointerException.class, () -> {
            dataProcessorService.processData(nullList);
        });
    }

    @Test
    void processData_emptyValue_emptyResult() {
        List<FruitRecord> empty = new ArrayList<>();
        dataProcessorService.processData(empty);
        int expectedSize = 0;
        assertEquals(expectedSize, storage.size());
    }
}
