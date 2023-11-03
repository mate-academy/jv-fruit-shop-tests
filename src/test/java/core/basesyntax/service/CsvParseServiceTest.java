package core.basesyntax.service;

import core.basesyntax.model.GoodsOperation;
import core.basesyntax.service.imp.CsvParser;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvParseServiceTest {
    private static CsvParseService csvParseService;

    @BeforeAll
    static void beforeAll() {
        csvParseService = new CsvParser();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listOperationsFromCsv_validText_ok() {
        List<String> inputList = List.of("b,banana,20", "b,apple,100");
        List<GoodsOperation> expected = List.of(
                new GoodsOperation(GoodsOperation.TransactionType.BALANCE, "banana", 20),
                new GoodsOperation(GoodsOperation.TransactionType.BALANCE, "apple", 100));
        List<GoodsOperation> actual = csvParseService.listOperationsFromCsv(inputList);
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void convertToOperationFromCsv_validText_ok() {
        String input = "b,banana,20";
        GoodsOperation expected = new GoodsOperation(
                GoodsOperation.TransactionType.BALANCE,
                "banana",
                20);
        GoodsOperation actual = csvParseService.convertToOperationFromCsv(input);
        Assertions.assertEquals(expected, actual);
    }
}
