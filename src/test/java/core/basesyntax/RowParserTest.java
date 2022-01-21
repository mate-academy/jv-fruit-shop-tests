package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.files.RowParser;
import core.basesyntax.service.files.RowParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowParserTest {
    private static final FruitRecord.Type BALANCE = FruitRecord.Type.BALANCE;
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;
    private static final FruitRecord.Type RETURN = FruitRecord.Type.RETURN;
    private static final FruitRecord.Type PURCHASE = FruitRecord.Type.PURCHASE;
    private static List<String> fileData;
    private static List<FruitRecord> fruitRecords;
    private static RowParser rowParser;

    @Before
    public void setUp() {
        rowParser = new RowParserImpl();
        fruitRecords = new ArrayList<>();
        fruitRecords.add(new FruitRecord(20, BALANCE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(100, BALANCE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(100, SUPPLY, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(13, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(10, RETURN, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(20, PURCHASE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(5, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(50, SUPPLY, new Fruit("banana")));
        fileData = new ArrayList<>();
        fileData.add("b,banana,20");
        fileData.add("b,apple,100");
        fileData.add("s,banana,100");
        fileData.add("p,banana,13");
        fileData.add("r,apple,10");
        fileData.add("p,apple,20");
        fileData.add("p,banana,5");
        fileData.add("s,banana,50");
    }

    @Test
    public void parseFruitRecordsFromFile_parse_OK() {
        List<FruitRecord> expected = fruitRecords;
        List<FruitRecord> actual = rowParser.parse(fileData);
        Assert.assertEquals("Test failed! "
                        + "Expected fruit records and actual fruit records are different!",
                expected, actual);
    }

    @Test
    public void parseFruitRecordsFromEmptyFile_parse_OK() {
        List<FruitRecord> expected = new ArrayList<>();
        List<FruitRecord> actual = rowParser.parse(new ArrayList<>());
        Assert.assertEquals("Test failed! "
                        + "Expected fruit records and actual fruit records are different!",
                expected, actual);
    }
}
