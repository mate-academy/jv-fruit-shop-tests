package core.basesyntax.service;

import core.basesyntax.model.GoodsOperation;
import core.basesyntax.service.imp.CsvParser;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvParseServiceTest {
    private static final String OPERATION_BALANCE_BANANA = "b,banana,20";
    private static final String OPERATION_BALANCE_APPLE = "b,apple,100";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    private static CsvParseService csvParseService;

    @BeforeAll
    static void beforeAll() {
        csvParseService = new CsvParser();
    }

    @Test
    void listOperationsFromCsv_validText_ok() {
        List<String> inputList = List.of(OPERATION_BALANCE_BANANA, OPERATION_BALANCE_APPLE);
        List<GoodsOperation> expected = List.of(
                new GoodsOperation(GoodsOperation.TransactionType.BALANCE, BANANA, 20),
                new GoodsOperation(GoodsOperation.TransactionType.BALANCE, APPLE, 100));
        List<GoodsOperation> actual = csvParseService.listOperationsFromCsv(inputList);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void convertToOperationFromCsv_validText_ok() {
        GoodsOperation expected = new GoodsOperation(
                GoodsOperation.TransactionType.BALANCE,
                BANANA,
                20);
        GoodsOperation actual = csvParseService.convertToOperationFromCsv(OPERATION_BALANCE_BANANA);
        Assertions.assertEquals(expected, actual);
    }
}
