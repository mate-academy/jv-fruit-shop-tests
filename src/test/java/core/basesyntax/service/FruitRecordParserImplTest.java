package core.basesyntax.service;

import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FruitRecordParserImplTest {
    private FruitRecordParser fruitRecordParser = new FruitRecordParserImpl();

    @Test
    public void parse_WithValidData_Ok() {
        List<String[]> splitedInformationList = List.of(
                new String[]{"type", "fruit", "quantity"},
                new String[]{"b", "banana", "20"},
                new String[]{"b", "apple", "100"},
                new String[]{"r", "apple", "10"},
                new String[]{"s", "banana", "100"},
                new String[]{"p", "banana", "13"});
        List<FruitRecord> actual = fruitRecordParser.parse(splitedInformationList);
        List<FruitRecord> expected = List.of(
                new FruitRecord("b", "banana", 20),
                new FruitRecord("b", "apple", 100),
                new FruitRecord("r", "apple", 10),
                new FruitRecord("s", "banana", 100),
                new FruitRecord("p", "banana", 13));
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WithNegativeValue_ExceptionOk() {
        List<String[]> splitedInformationList = List.of(
                new String[]{"type", "fruit", "quantity"},
                new String[]{"b", "banana", "20"},
                new String[]{"b", "apple", "100"},
                new String[]{"s", "banana", "100"},
                new String[]{"p", "banana", "-13"});
        fruitRecordParser.parse(splitedInformationList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WithNotValidOperation_ExceptionOk() {
        List<String[]> splitedInformationList = List.of(
                new String[]{"type", "fruit", "quantity"},
                new String[]{"b", "banana", "20"},
                new String[]{"b", "apple", "100"},
                new String[]{"rtrtyry", "banana", "100"},
                new String[]{"p", "banana", "13"});
        fruitRecordParser.parse(splitedInformationList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WithNotValidString_ExceptionOk() {
        List<String[]> splitedInformationList = List.of(
                new String[]{"type", "fruit", "quantity"},
                new String[]{"b", "banana", "20"},
                new String[]{"b", "100"},
                new String[]{"s", "banana", "100"},
                new String[]{"p", "banana", "12"});
        fruitRecordParser.parse(splitedInformationList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WithEmptyString_ExceptionOk() {
        List<String[]> splitedInformationList = List.of(
                new String[]{"type", "fruit", "quantity"},
                new String[]{"b", "banana", "20"},
                new String[]{},
                new String[]{"s", "banana", "100"},
                new String[]{"p", "banana", "13"});
        fruitRecordParser.parse(splitedInformationList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WithEmptyList_ExceptionOk() {
        List<String[]> splitedInformationList = new ArrayList<>();
        fruitRecordParser.parse(splitedInformationList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WithNotCorrectFirstLine_ExceptionOk() {
        List<String[]> splitedInformationList = List.of(
                new String[]{"ty", "frui", "quantity"},
                new String[]{"b", "banana", "20"},
                new String[]{"b", "apple", "100"},
                new String[]{"s", "banana", "100"},
                new String[]{"r", "banana", "13"});
        fruitRecordParser.parse(splitedInformationList);
    }
}
