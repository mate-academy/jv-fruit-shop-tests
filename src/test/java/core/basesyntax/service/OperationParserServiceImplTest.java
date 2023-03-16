package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationParserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperationParserServiceImplTest {
    private OperationParserService operationParserService;

    @BeforeEach
    public void setUp() {
        operationParserService = new OperationParserServiceImpl();
    }

    @Test
    public void parseOperation_validData_ok() {
        List<String> inputData = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,20",
                "s,banana,100",
                "p,apple,5",
                "r,orange,50"
        );
        List<FruitTransaction> expectedResult = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 50)
        );
        List<FruitTransaction> actualResult = operationParserService.parseOperation(inputData);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void parseOperation_invalidRowLength_notOk() {
        List<String> inputData = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,20,fuuuu"
        );
        assertThrows(RuntimeException.class, () -> operationParserService.parseOperation(inputData));
    }

    @Test
    public void parseOperation_invalidFruitName_notOk() {
        List<String> inputData = Arrays.asList(
                "operation,fruit,quantity",
                "b,,20"
        );
        assertThrows(RuntimeException.class, () -> operationParserService.parseOperation(inputData));
    }

    @Test
    public void parseOperation_invalidAmount_notOk() {
        List<String> inputData = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,-20"
        );
        assertThrows(RuntimeException.class, () -> operationParserService.parseOperation(inputData));
    }

    @Test
    public void parseOperation_NonNumeric_notOk() {
        List<String> inputData = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,your ad can be here"
        );
        assertThrows(RuntimeException.class, () -> operationParserService.parseOperation(inputData));
    }
}