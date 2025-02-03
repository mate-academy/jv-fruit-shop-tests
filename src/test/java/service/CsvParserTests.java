package service;

import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvParserTests {
    private CsvParseService csvParserService;

    @BeforeEach
    void setUp() {
        csvParserService = new CsvParseService();
    }

    @Test
    void parseTransaction_ShouldThrowException_WhenInvalidFormat() {
        String wrongLineFormat = "b,lemon,100,sold";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            csvParserService.parseTransaction(wrongLineFormat);
        });

        Assertions.assertTrue(exception.getMessage().contains("Invalid CSV format: " + wrongLineFormat));
    }

    @Test
    void parseTransaction_ShouldReturnCorrectTransaction_WhenValidFormat() {
        FruitTransaction transactionBalance =
                new FruitTransaction("lemon", 100, FruitTransaction.Operation.BALANCE);
        String wrongLineFormat = "b,lemon,100";
        FruitTransaction resultOfParse = csvParserService.parseTransaction(wrongLineFormat);
        Assertions.assertEquals(transactionBalance.getFruit(), resultOfParse.getFruit());
        Assertions.assertEquals(transactionBalance.getQuantity(), resultOfParse.getQuantity());
        Assertions.assertEquals(transactionBalance.getOperation(), resultOfParse.getOperation());
    }
}
