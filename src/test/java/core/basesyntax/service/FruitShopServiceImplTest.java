package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Storage;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.operations.AdditionHendlerImpl;
import core.basesyntax.service.operations.OperationHendler;
import core.basesyntax.service.operations.SubstractionHendlerImpl;
import core.basesyntax.service.strategy.OperationsStrategy;
import core.basesyntax.service.strategy.OperationsStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final Map<TransactionDto.OperationTypes, OperationHendler> strategy =
            new HashMap<>();
    private static final OperationsStrategy operationsStrategy =
            new OperationsStrategyImpl(strategy);
    private static final Map<String, Integer> FRUIT_COUNT = Storage.FRUIT_COUNT;
    private final FruitShopService fruitShopService =
            new FruitShopServiceImpl(operationsStrategy);
    private List<TransactionDto> testList;

    @BeforeClass
    public static void beforeClass() throws Exception {
        strategy.put(TransactionDto.OperationTypes.BALANCE, new AdditionHendlerImpl());
        strategy.put(TransactionDto.OperationTypes.PURCHASE, new SubstractionHendlerImpl());
        strategy.put(TransactionDto.OperationTypes.RETURN, new AdditionHendlerImpl());
        strategy.put(TransactionDto.OperationTypes.SUPPLY, new AdditionHendlerImpl());
    }

    @Test
    public void applyOperationsOnFruitsDto_validInputAdd_ok() {
        testList = new ArrayList<>();
        testList.add(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 100));
        testList.add(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 200));
        fruitShopService.applyOperationsOnFruitsDto(testList);
        int expected = 300;
        int actual = FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        FRUIT_COUNT.clear();
    }

    @Test
    public void applyOperationsOnFruitsDto_validInputSub_ok() {
        testList = new ArrayList<>();
        testList.add(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 100));
        testList.add(new TransactionDto(TransactionDto.OperationTypes.PURCHASE, "apple", 50));
        fruitShopService.applyOperationsOnFruitsDto(testList);
        int expected = 50;
        int actual = FRUIT_COUNT.get("apple");
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        FRUIT_COUNT.clear();
    }

    @Test
    public void applyOperationsOnFruitsDto_invalidInputAdd_ok() {
        testList = new ArrayList<>();
        testList.add(new TransactionDto(TransactionDto.OperationTypes.PURCHASE, "apple", 200));
        assertThrows("Method should return RuntimeExeption when result less than zero",
                RuntimeException.class,
                () -> fruitShopService.applyOperationsOnFruitsDto(testList));
        FRUIT_COUNT.clear();
    }
}
