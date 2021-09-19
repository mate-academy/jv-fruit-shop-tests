package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.RecordListMakerService;
import core.basesyntax.service.impl.RecordListMakerServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecordListMakerServiceTest {
    private static final FruitRecord appleRecordBalance = new FruitRecord(FruitRecord.Operation.BALANCE, new Fruit("apple"), 30);
    private static final FruitRecord bananaRecordBalance = new FruitRecord(FruitRecord.Operation.BALANCE, new Fruit("banana"), 30);
    private static final FruitRecord appleRecordReturn = new FruitRecord(FruitRecord.Operation.RETURN, new Fruit("apple"), 10);
    private static final List<FruitRecord> expected = List.of(appleRecordBalance, bananaRecordBalance, appleRecordReturn);
    private static final String FIRST_ROW = "type,fruit,quantity";
    private static final String APPLE_BALANCE_ROW = "b,apple,30";
    private static final String APPLE_RETURN_ROW = "r,apple,10";
    private static final String BANANA_BALANCE_ROW = "b,banana,30";
    private static final String WRONG_ROW = "a,banana,45";
    private static RecordListMakerService recordListMakerService;

    @BeforeAll
    public static void initialize() {
        recordListMakerService = new RecordListMakerServiceImpl();
    }

    @Test
    public void makingRecordListFromCorrectRows_Ok() {
        List<String> correctRows = List.of(FIRST_ROW, APPLE_BALANCE_ROW, BANANA_BALANCE_ROW, APPLE_RETURN_ROW);
        List<FruitRecord> actual = recordListMakerService.getFruitRecordList(correctRows);
        assertEquals(actual, expected);
    }

    @Test
    public void makingRecordListFromRowsWithOutFirstRow_notOk() {
        List<String> rowsWithOutFirstCorrectRow = List.of(APPLE_BALANCE_ROW, BANANA_BALANCE_ROW, APPLE_RETURN_ROW);
        List<FruitRecord> actual = recordListMakerService.getFruitRecordList(rowsWithOutFirstCorrectRow);
        assertNotEquals(actual, expected);
    }

    @Test
    public void makingRecordListFromNotValidRows_notOk() {
        List<String> rowsWithInvalidData = List.of(FIRST_ROW, WRONG_ROW, BANANA_BALANCE_ROW, APPLE_RETURN_ROW);
        assertThrows(RuntimeException.class, () -> recordListMakerService.getFruitRecordList(rowsWithInvalidData));
    }
}
