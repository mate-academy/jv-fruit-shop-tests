package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.DataProcessorService;
import core.basesyntax.servise.impl.DataProcessorServiceImpl;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.testclasses.DaoStorageForTest;
import core.basesyntax.testclasses.OperationStrategyForTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataProcessorServiceTest {
    static final String FRUIT = "banana";
    static final int QUANTITY = 100;
    private static DataProcessorService dataProcessorService;
    private List<FruitTransaction> emptyList;
    private List<FruitTransaction> listForTest;

    @BeforeAll
    public static void setUp() {
        dataProcessorService = new DataProcessorServiceImpl(new OperationStrategyForTest());
    }

    @BeforeEach
    public void beforeTest() {
        emptyList = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction("b", FRUIT, QUANTITY);
        listForTest = List.of(transaction);
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
        assertEquals(QUANTITY, new DaoStorageForTest().getValue(FRUIT));
    }
}
