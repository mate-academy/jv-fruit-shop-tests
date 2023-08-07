package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParserService;
import core.basesyntax.util.DataValidator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataParserServiceImplTest {
    private DataParserService dataParserService;
    private DataValidator dataValidatorMock;
    private List<String> testData;

    @BeforeEach
    void setUp() {
        dataValidatorMock = mock(DataValidator.class);
        dataParserService = new DataParserServiceImpl(dataValidatorMock);
        testData = new ArrayList<>();
        testData.add("fruit,quantity");
        testData.add("r,apple,10");
        testData.add("s,banana,5");
    }

    @Test
    void createFruitTransaction_validData_ok() {
        List<FruitTransaction> result = dataParserService.createFruitTransaction(testData);
        System.out.println(result.size());
        assertEquals(2, result.size());
        assertEquals(Operation.RETURN, result.get(0).getOperationType());
        assertEquals("apple", result.get(0).getFruitName());
        assertEquals(10, result.get(0).getFruitQuantity());
        assertEquals(Operation.SUPPLY, result.get(1).getOperationType());
        assertEquals("banana", result.get(1).getFruitName());
        assertEquals(5, result.get(1).getFruitQuantity());
    }

    @Test
    void createFruitTransaction_emptyFruitQuantity_notOk() {
        testData.clear();
        testData.add("fruit,quantity");
        testData.add("r,apple,10");
        testData.add("s,banana");
        assertThrows(IllegalArgumentException.class,
                () -> dataParserService.createFruitTransaction(testData));
    }

    @Test
    void createFruitTransaction_emptyFruitName_notOk() {
        testData.clear();
        testData.add("fruit,quantity");
        testData.add("r,10");
        testData.add("s,banana,5");
        assertThrows(IllegalArgumentException.class,
                () -> dataParserService.createFruitTransaction(testData));
    }

    @Test
    void createFruitTransaction_emptyOperationType_notOk() {
        testData.clear();
        testData.add("fruit,quantity");
        testData.add("apple,10");
        testData.add("s,banana,5");
        assertThrows(IllegalArgumentException.class,
                () -> dataParserService.createFruitTransaction(testData));
    }
}
