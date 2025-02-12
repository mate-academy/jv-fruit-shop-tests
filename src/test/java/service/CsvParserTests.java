package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvParserTests {

    private CsvParseService csvParseService;
    
    @BeforeEach
    void setUp() {
        csvParseService = new CsvParseService();
    }

    @Test
    void parseTransaction_ShouldThrowException_WhenInvalidFormat() {
        String wrongLineFormat = "b,lemon,100,sold";
        Exception exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            csvParseService.parseTransaction(wrongLineFormat);
                        });

        assertTrue(
                exception.getMessage().contains("Invalid CSV format: " + wrongLineFormat));
    }

    @Test
    void parseTransaction_ShouldReturnCorrectTransaction_WhenValidFormat() {
        FruitTransaction transactionBalance =
                new FruitTransaction("lemon", 100, FruitTransaction.Operation.BALANCE);
        String wrongLineFormat = "b,lemon,100";
        FruitTransaction resultOfParse = csvParseService.parseTransaction(wrongLineFormat);
        assertEquals(transactionBalance.getFruit(), resultOfParse.getFruit());
        assertEquals(transactionBalance.getQuantity(), resultOfParse.getQuantity());
        assertEquals(transactionBalance.getOperation(), resultOfParse.getOperation());
    }

    @Test
    void parseTransaction_WhenInvalidFormat_ShouldThrowException() {
        String invalidLine = "apple,10";
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> csvParseService.parseTransaction(invalidLine)
        );
        assertTrue(exception.getMessage().contains("Invalid CSV format"));
    }

    @Test
    void parseTransaction_WhenValidFormat_ShouldReturnFruitTransaction() {
        String validLine = "s,apple,10";
        FruitTransaction transaction = csvParseService.parseTransaction(validLine);
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }
}
