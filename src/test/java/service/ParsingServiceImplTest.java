package service;

import java.util.ArrayList;
import java.util.List;
import model.FruitRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParsingServiceImplTest {
    private static List<FruitRecord> expectedList;
    private static List<String> rawData;
    private static ParsingService parsingService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        parsingService = new ParsingServiceImpl();
        rawData = new ArrayList<>();
        expectedList = new ArrayList<>();
    }

    @After
    public void tearDown() {
        rawData.clear();
        expectedList.clear();
    }

    @Test
    public void parse_Ok() {
        rawData.add("type,fruit,quantity");
        rawData.add("b,banana,20");
        rawData.add("b,apple,100");
        rawData.add("b,watermelon,5");
        rawData.add("s,banana,100");
        rawData.add("b,apricot,0");
        rawData.add("b,grape,10");
        rawData.add("p,banana,13");
        rawData.add("r,apple,10");
        rawData.add("p,apple,20");
        rawData.add("p,banana,5");
        rawData.add("s,banana,50");
        rawData.add("p,watermelon,2");
        rawData.add("s,apricot,20");
        rawData.add("s,watermelon,5");
        rawData.add("p,apricot,5");
        rawData.add("s,grape,10");
        rawData.add("r,apricot,2");
        rawData.add("p,apricot,5");
        rawData.add("p,grape,5");
        expectedList.add(new FruitRecord("b","banana",20));
        expectedList.add(new FruitRecord("b","apple",100));
        expectedList.add(new FruitRecord("b","watermelon",5));
        expectedList.add(new FruitRecord("s","banana",100));
        expectedList.add(new FruitRecord("b","apricot",0));
        expectedList.add(new FruitRecord("b","grape",10));
        expectedList.add(new FruitRecord("p","banana",13));
        expectedList.add(new FruitRecord("r","apple",10));
        expectedList.add(new FruitRecord("p","apple",20));
        expectedList.add(new FruitRecord("p","banana",5));
        expectedList.add(new FruitRecord("s","banana",50));
        expectedList.add(new FruitRecord("p","watermelon",2));
        expectedList.add(new FruitRecord("s","apricot",20));
        expectedList.add(new FruitRecord("s","watermelon",5));
        expectedList.add(new FruitRecord("p","apricot",5));
        expectedList.add(new FruitRecord("s","grape",10));
        expectedList.add(new FruitRecord("r","apricot",2));
        expectedList.add(new FruitRecord("p","apricot",5));
        expectedList.add(new FruitRecord("p","grape",5));
        List<FruitRecord> actualList = parsingService.parse(rawData);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void parseWhitespaceData_Ok() {
        rawData.add("  type , fruit , quantity ");
        rawData.add("    b  , banana  , 20");
        rawData.add("  b   ,apple ,100  ");
        rawData.add("s, banana, 100");
        rawData.add("  p  , banana ,13");
        rawData.add("r,apple,10");
        rawData.add("    p    ,   apple, 20      ");
        expectedList.add(new FruitRecord("b", "banana", 20));
        expectedList.add(new FruitRecord("b", "apple", 100));
        expectedList.add(new FruitRecord("s", "banana", 100));
        expectedList.add(new FruitRecord("p", "banana", 13));
        expectedList.add(new FruitRecord("r", "apple", 10));
        expectedList.add(new FruitRecord("p", "apple", 20));
        List<FruitRecord> actualList = parsingService.parse(rawData);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void parseNoCaptionData_Ok() {
        rawData.add("b,banana,20");
        rawData.add("b,apple,100");
        rawData.add("b,watermelon,5");
        rawData.add("s,banana,100");
        rawData.add("b,apricot,0");
        rawData.add("b,grape,10");
        rawData.add("p,banana,13");
        rawData.add("r,apple,10");
        rawData.add("p,apple,20");
        rawData.add("p,banana,5");
        expectedList.add(new FruitRecord("b", "banana", 20));
        expectedList.add(new FruitRecord("b", "apple", 100));
        expectedList.add(new FruitRecord("b", "watermelon", 5));
        expectedList.add(new FruitRecord("s", "banana", 100));
        expectedList.add(new FruitRecord("b", "apricot", 0));
        expectedList.add(new FruitRecord("b", "grape", 10));
        expectedList.add(new FruitRecord("p", "banana", 13));
        expectedList.add(new FruitRecord("r", "apple", 10));
        expectedList.add(new FruitRecord("p", "apple", 20));
        expectedList.add(new FruitRecord("p", "banana", 5));
        List<FruitRecord> actualList = parsingService.parse(rawData);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void parseIncorrectData_NotOk() {
        rawData.add("type,fruit,quantity");
        rawData.add("b,watermelon");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Incorrect format of the line: [b, watermelon]");
        parsingService.parse(rawData);
    }

    @Test
    public void parseNoActivity_NotOk() {
        rawData.add("type,fruit,quantity");
        rawData.add(",watermelon,10");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Activity is empty");
        parsingService.parse(rawData);
    }

    @Test
    public void parseNoFruitName_NotOk() {
        rawData.add("type,fruit,quantity");
        rawData.add("b,,10");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Fruit name is empty");
        parsingService.parse(rawData);
    }

    @Test
    public void parseIncorrectAmount_NotOk() {
        rawData.add("type,fruit,quantity");
        rawData.add("b,banana,1o");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Incorrect number: 1o");
        parsingService.parse(rawData);
    }

    @Test
    public void parseNegativeAmount_NotOk() {
        rawData.add("type,fruit,quantity");
        rawData.add("b,banana,-10");
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Incorrect amount: -10");
        parsingService.parse(rawData);
    }

    @Test
    public void parseNullData_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Data for parsing is null");
        parsingService.parse(null);

    }

    @Test
    public void parseEmptyData_Ok() {
        List<FruitRecord> actualList = parsingService.parse(rawData);
        Assert.assertEquals(expectedList, actualList);
    }
}
