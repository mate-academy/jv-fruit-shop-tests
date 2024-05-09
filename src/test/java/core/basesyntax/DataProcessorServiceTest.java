package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.DataProcessorService;
import core.basesyntax.servise.impl.DataProcessorServiceImpl;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataProcessorServiceTest {
    private static final String FRUIT = "banana";
    private static final String CODE = "b";
    private static final int QUANTITY = 100;
    private static DataProcessorService dataProcessorService;
    private static Map<String, Integer> storageForTest;
    private List<FruitTransaction> emptyList;
    private List<FruitTransaction> listForTest;

    @BeforeAll
    public static void setUp() {
        storageForTest = new HashMap<>();

        OperationStrategy operationStrategyForTest = fruitTransaction
                -> (OperationService) transaction
                        -> storageForTest.put(FRUIT, QUANTITY);

        dataProcessorService = new DataProcessorServiceImpl(operationStrategyForTest);
    }

    @BeforeEach
    public void beforeTest() {
        emptyList = new ArrayList<>();
        listForTest = List.of(new FruitTransaction(CODE, FRUIT, QUANTITY));
    }

    @Test
    public void dataProcessorService_strategyNull_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new DataProcessorServiceImpl(null));
    }

    @Test
    public void dataProcessorService_ListOfTransactionsNullOrEmpty_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dataProcessorService.processingData(null));
        assertThrows(IllegalArgumentException.class,
                () -> dataProcessorService.processingData(emptyList));
    }

    @Test
    public void dataProcessorService_processingData_Ok() {
        dataProcessorService.processingData(listForTest);
        assertEquals(QUANTITY, storageForTest.get(FRUIT));
    }
}
