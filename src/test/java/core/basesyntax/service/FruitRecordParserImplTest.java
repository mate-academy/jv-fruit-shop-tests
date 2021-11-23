package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordParserImplTest {
    private static FruitRecordParser fruitRecordParser;
    private static List<String> dataList;
    private static List<FruitRecord> expectedFruitRecordList;
    private List<FruitRecord> actual;

    @BeforeClass
    public static void setUp() {
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
    public void checkStringToFruitRecord_Ok() {
        actual = fruitRecordParser.parserFruitRecords(dataList);
        assertEquals(expectedFruitRecordList, actual);
    }

    @Test(expected = ValidationException.class)
    public void checkStringToFruitRecord_NotOk() {
        dataList.add("r,banana,-5");
        fruitRecordParser.parserFruitRecords(dataList);
    }

}
