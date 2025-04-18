package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import model.FruitTransaction;
import model.FruitTransaction.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.ParseServiceImpl;

public class ParseServiceTest {
    private static ParseService parseService;

    @BeforeEach
    void create() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseCsvLine_withEmptyLine_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(list));
        assertEquals("Input line cannot be null or empty", exception.getMessage());
    }

    @Test
    void parseCsvLine_withInvalidQuantity_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,apple");
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                () -> parseService.parseList(list));
        assertEquals("Invalid quantity format in line", exception.getMessage());
    }

    @Test
    void parseCsvLine_withInvalidFieldCount_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "b,banana");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(list));
        assertEquals("Invalid CSV format: expected 3 fields, but got 2 in line: b,banana",
                illegalArgumentException.getMessage());
    }

    @Test
    void parseList_withInvalidOperation_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "w,banana,100", "s,banana,60",
                "p,banana,10", "r,banana,2", ",apple,80", "s,apple,20", "p,apple,10");
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                parseService.parseList(list));
        Assertions.assertEquals("Unsupported operation code: w", exception.getMessage());
    }

    @Test
    void parseList_withNullOperation_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", ",banana,100");
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> parseService.parseList(list));
        Assertions.assertEquals("Unsupported operation code: ", exception.getMessage());
    }

    @Test
    void parseCsvLine_withEmptyFruit_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "b, ,100");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(list));
        assertEquals("Fruit name cannot be empty in line: b, ,100",
                exception.getMessage());
    }

    @Test
    void parseCsvLine_withNegativeQuantity_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,-200");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(list));
        assertEquals("Quantity cannot be negative or zero in line: b,banana,-200",
                exception.getMessage());
    }

    @Test
    void parseCsvLine_withZeroQuantity_throwsException() {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,0");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(list));
        assertEquals("Quantity cannot be negative or zero in line: b,banana,0",
                illegalArgumentException.getMessage());
    }

    @Test
    void parseCsvLine_withValidData_createsFruitTransaction() {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,100");
        List<FruitTransaction> transactions = parseService.parseList(list);
        assertEquals(1, transactions.size());
        FruitTransaction fruitTransaction = transactions.get(0);
        assertEquals(OperationType.fromCode("b"), fruitTransaction.getOperationType());
        assertEquals("banana", fruitTransaction.getName());
        assertEquals(100, fruitTransaction.getQuantity());
    }
}
