package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitRecordParserImplTest {
    private static FruitRecordParser fruitRecordParser;
    private static List<String> dataList;
    private static List<FruitRecord> expectedFruitRecordList;
    private List<FruitRecord> actual;

    @BeforeAll
    static void setUp() {
        fruitRecordParser = new FruitRecordParserImpl();
        dataList = new ArrayList<>();
        dataList.add("b,banana,10");
        dataList.add("s,banana,10");
        dataList.add("p,banana,15");
        dataList.add("r,banana,5");
        expectedFruitRecordList = new ArrayList<>();
        expectedFruitRecordList.add(new FruitRecord("b", "banana", 10));
        expectedFruitRecordList.add(new FruitRecord("s", "banana", 10));
        expectedFruitRecordList.add(new FruitRecord("p", "banana", 15));
        expectedFruitRecordList.add(new FruitRecord("r", "banana", 5));
    }

    @Test
    void checkStringToFruitRecord_Ok() {
        actual = fruitRecordParser.parserFruitRecords(dataList);
        assertEquals(expectedFruitRecordList, actual);
    }

    @Test
    void checkStringToFruitRecord_NotOk() {
        dataList.add("r,banana,-5");
        assertThrows(ValidationException.class,
                () -> fruitRecordParser.parserFruitRecords(dataList));
    }

}
