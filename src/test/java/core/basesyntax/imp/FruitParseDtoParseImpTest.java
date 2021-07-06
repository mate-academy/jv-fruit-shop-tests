package core.basesyntax.imp;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitParse;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParseDtoParseImpTest {
    private static List<FruitRecordDto> listRecord;
    private static FruitParse parseFruit;
    private static CustomFileReadImp reader;
    private static List<String> list;

    @BeforeClass
    public static void beforeAll() {
        listRecord = new ArrayList<>();
        parseFruit = new FruitParseDtoParseImp();
        reader = new CustomFileReadImp();
    }

    @Before
    public void beforeEach() {
        listRecord.add(new FruitRecordDto("b", new Fruit("banana"), 20));
        listRecord.add(new FruitRecordDto("b", new Fruit("apple"), 100));
    }

    @After
    public void afterEach() {
        listRecord.clear();
    }

    @Test
    public void rightParse_ok() {
        list = reader.readFromFile("src/test/right.file.csv");
        Assert.assertEquals(listRecord, parseFruit.parse(list));
    }

    @Test
    public void fileWithSpace_ok() {
        list = reader.readFromFile("src/test/file.with.space.csv");
        Assert.assertEquals(listRecord, parseFruit.parse(list));
    }

    @Test(expected = RuntimeException.class)
    public void emptyParse_notOk() {
        list = reader.readFromFile("src/test/empty.csv");
        parseFruit.parse(list);
    }

    @Test(expected = RuntimeException.class)
    public void fileWithNameFruitNumber_notOk() {
        listRecord.add(new FruitRecordDto("b", new Fruit("bana12na"), 20));
        list = reader.readFromFile("src/test/file.with.number.csv");
        parseFruit.parse(list);
    }
}
