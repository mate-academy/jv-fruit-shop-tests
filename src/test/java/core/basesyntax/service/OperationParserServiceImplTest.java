package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationParserServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationParserServiceImplTest {
    private static final String HEADER = "operation,fruit,quantity";
    private static final String VALID_OPERATION_1 = "b,apple,20";
    private static final String VALID_OPERATION_2 = "s,banana,100";
    private static final String VALID_OPERATION_3 = "p,apple,5";
    private static final String VALID_OPERATION_4 = "r,orange,50";
    private static final String INVALID_ROW_LENGTH = "b,apple,20,fuuuu";
    private static final String INVALID_FRUIT_NAME = "b,,20";
    private static final String INVALID_AMOUNT = "b,apple,-20";
    private static final String NON_NUMERIC_AMOUNT = "b,apple,your ad can be here";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final int VALUE1 = 20;
    private static final int VALUE2 = 100;
    private static final int VALUE3 = 5;
    private static final int VALUE4 = 50;

    private OperationParserService operationParserService;

    @BeforeEach
    public void setUp() {
        operationParserService = new OperationParserServiceImpl();
    }

    @Test
    public void parseOperation_validData_ok() {
        List<String> inputData = Arrays.asList(
                HEADER,
                VALID_OPERATION_1,
                VALID_OPERATION_2,
                VALID_OPERATION_3,
                VALID_OPERATION_4
        );
        List<FruitTransaction> expectedResult = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, VALUE1),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, VALUE2),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, VALUE3),
                new FruitTransaction(FruitTransaction.Operation.RETURN, ORANGE, VALUE4)
        );
        List<FruitTransaction> actualResult = operationParserService.parseOperation(inputData);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void parseOperation_invalidRowLength_notOk() {
        List<String> inputData = Arrays.asList(
                HEADER,
                INVALID_ROW_LENGTH
        );
        assertThrows(RuntimeException.class, () ->
                operationParserService.parseOperation(inputData));
    }

    @Test
    public void parseOperation_invalidFruitName_notOk() {
        List<String> inputData = Arrays.asList(
                HEADER,
                INVALID_FRUIT_NAME
        );
        assertThrows(RuntimeException.class, () ->
                operationParserService.parseOperation(inputData));
    }

    @Test
    public void parseOperation_invalidAmount_notOk() {
        List<String> inputData = Arrays.asList(
                HEADER,
                INVALID_AMOUNT
        );
        assertThrows(RuntimeException.class, () ->
                operationParserService.parseOperation(inputData));
    }

    @Test
    public void parseOperation_NonNumeric_notOk() {
        List<String> inputData = Arrays.asList(
                HEADER,
                NON_NUMERIC_AMOUNT
        );
        assertThrows(RuntimeException.class, () ->
                operationParserService.parseOperation(inputData));
    }
}
