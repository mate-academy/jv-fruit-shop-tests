package core.basesyntax.service.processor;

import static core.basesyntax.database.Storage.storage;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.database.Storage;
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
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int TEN = 10;
    private static final int FIFTEEN = 15;
    private static final int ELEVEN = 11;
    private static final int FIFTY = 50;
    private static final int NINE = 9;
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
        Storage.storage.clear();
    }

    @Test
    void processData_validInput_Success() {
        List<FruitRecord> fruitRecordList = List.of(
                new FruitRecord(FruitRecord.Operation.BALANCE, BANANA, TEN),
                new FruitRecord(FruitRecord.Operation.SUPPLY, BANANA, FIFTEEN),
                new FruitRecord(FruitRecord.Operation.PURCHASE, BANANA, FIFTEEN),
                new FruitRecord(FruitRecord.Operation.RETURN, BANANA, TEN)
        );

        dataProcessorService.processData(fruitRecordList);
        int actualBananaQuantity = storage.get("banana");
        assertEquals(20, actualBananaQuantity);
    }

    @Test
    void processData_negativeResult_Failure() {
        List<FruitRecord> fruitRecords = List.of(
                new FruitRecord(FruitRecord.Operation.BALANCE, APPLE, TEN),
                new FruitRecord(FruitRecord.Operation.SUPPLY, APPLE, ELEVEN),
                new FruitRecord(FruitRecord.Operation.PURCHASE, APPLE, FIFTY),
                new FruitRecord(FruitRecord.Operation.RETURN, APPLE, NINE)
        );

        dataProcessorService.processData(fruitRecords);
        int result = storage.get("apple");
        int expectedNegativeValue = - 20;
        assertTrue(result < 0, "The result should be negative");
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
        assertTrue(storage.isEmpty());
    }
}
