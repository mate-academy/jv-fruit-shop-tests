package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadServiceTests {
    private CsvReadService csvReadService;

    @BeforeEach
    void setUp() {
        csvReadService = new CsvReadService();
    }

    @Test
    void parseTransaction_WhenWrongLine() {
                String wrongFileName = "";
                Exception exception =
                Assertions.assertThrows(
                RuntimeException.class,
                () -> {csvReadService.readTransactionsFromCsv(wrongFileName);}
            );

            Assertions.assertTrue(exception.getMessage().contains("Error reading CSV file: "));
        }
    }
