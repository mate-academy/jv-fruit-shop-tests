package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.TypeActivity;
import core.basesyntax.service.impl.FileParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class FileParserTest {
    private static FileParser fileParser;
    private static List<String> listOfParser;
    private static List<FruitOperation> expect;

    @BeforeClass
    public static void beforeClass() {
        fileParser = new FileParserImpl();
        expect = new ArrayList<>();
        expect.add(new FruitOperation(TypeActivity.typeGiven("b"), new Fruit("banana"), 20));
        expect.add(new FruitOperation(TypeActivity.typeGiven("b"), new Fruit("apple"), 100));
        expect.add(new FruitOperation(TypeActivity.typeGiven("s"), new Fruit("banana"), 100));
        expect.add(new FruitOperation(TypeActivity.typeGiven("p"), new Fruit("banana"), 13));
        expect.add(new FruitOperation(TypeActivity.typeGiven("r"), new Fruit("apple"), 10));
        expect.add(new FruitOperation(TypeActivity.typeGiven("p"), new Fruit("apple"), 20));
        expect.add(new FruitOperation(TypeActivity.typeGiven("p"), new Fruit("banana"), 5));
        expect.add(new FruitOperation(TypeActivity.typeGiven("s"), new Fruit("banana"), 50));
    }

    @Test
    public void FileParserCorrect_Ok() {
        listOfParser = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<FruitOperation> actual = fileParser.parse(listOfParser);
        assertEquals(expect, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataIsNull_NotOk() {
        fileParser.parse(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseTypeActivityIsEmpty_NotOk() {
        listOfParser = List.of("type,fruit,quantity", ",banana,20");
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseTypeActivityIsBlank_NotOk() {
        listOfParser = List.of("type,fruit,quantity", "   ,banana,20");
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseTypeActivityNull_NotOk() {
        listOfParser = List.of("type,fruit,quantity", ",banana,20");
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseFruitIsEmpty_NotOk() {
        listOfParser = List.of("type,fruit,quantity", "p,,20");
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseFruitIsBlank_NotOk() {
        listOfParser = List.of("type,fruit,quantity", "p,   ,20");
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseQuantityZero_NotOk() {
        listOfParser = List.of("type,fruit,quantity", "p,banana,0");
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseQuantityNegative_NotOk() {
        listOfParser = List.of("type,fruit,quantity", "p,banana,-10");
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseStoreInformationNull_NotOk() {
        listOfParser = List.of("type,fruit,quantity", null);
        fileParser.parse(listOfParser);
    }

    @Test(expected = RuntimeException.class)
    public void parseStoreInformationIncorect_NotOk() {
        listOfParser = List.of("type,fruit,quantity", "d,juce,tr");
        fileParser.parse(listOfParser);
    }
}
