package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final List<FruitTransaction> EMPTY_LIST_RESULT = new ArrayList<>();
    private static final List<FruitTransaction> LIST_RESULT = new ArrayList<>();
    private static final List<String> INPUT_REPORT = new ArrayList<>();
    private static final List<String> EMPTY_INPUT_REPORT = new ArrayList<>();

    @BeforeEach
    void setUp() {
        INPUT_REPORT.add("type,fruit,quantity");
        INPUT_REPORT.add("b,banana,20");
        INPUT_REPORT.add("r,apple,100");
        INPUT_REPORT.add("p,banana,100");
        INPUT_REPORT.add("s,banana,115");
        LIST_RESULT.add(new FruitTransaction("b","banana",20));
        LIST_RESULT.add(new FruitTransaction("r","apple",100));
        LIST_RESULT.add(new FruitTransaction("p","banana",100));
        LIST_RESULT.add(new FruitTransaction("s","banana",115));
    }

    @Test
    void convertEmptyList_OK() {
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> actualResult = dataConverter
                .convertToTransaction(EMPTY_INPUT_REPORT);
        Assertions.assertEquals(EMPTY_LIST_RESULT, actualResult,
                "Test failed! You should returned empty array.");
    }

    @Test
    void convertList_OK() {
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> actualResult = dataConverter.convertToTransaction(INPUT_REPORT);
        for (int i = 0; i < actualResult.size(); i++) {
            Assertions.assertEquals(LIST_RESULT.get(i).getOperation(),
                    actualResult.get(i).getOperation(),
                    "Test failed! Element is not added correctly at index "
                            + i
                            + " in Operation table");
            Assertions.assertEquals(LIST_RESULT.get(i).getFruit(),
                    actualResult.get(i).getFruit(),
                    "Test failed! Element is not added correctly at index "
                            + i
                            + " in Fruit table");
            Assertions.assertEquals(LIST_RESULT.get(i).getQuantity(),
                    actualResult.get(i).getQuantity(),
                    "Test failed! Element is not added correctly at index "
                            + i
                            + " in Quantity table");
        }
    }

    @Test
    void convertListThrowsExceptions_OK() {
        DataConverter dataConverter = new DataConverterImpl();
        INPUT_REPORT.set(2,"r,apple,text");
        Assertions.assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(INPUT_REPORT));
    }
}
