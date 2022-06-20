package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.ParseService;

public class ParseServiceTest {
    private static ParseService parseService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        parseService = new CsvParseServiceImpl();
    }

    @Test
    public void parse_parseCorrectFetchedData_ok() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"s", "apple", "35"});
        list.add(new String[]{"b", "apple", "50"});
        FruitTransaction transaction1 = new FruitTransaction("s", "apple", 35);
        FruitTransaction transaction2 = new FruitTransaction("b", "apple", 50);
        assertEquals("method parse didn't return expected quantity of objects",
                2, parseService.parse(list).size());
        assertEquals("method parse didn't return correspond result",
                transaction1, parseService.parse(list).get(0));
        assertEquals("method parse didn't return correspond result",
                transaction2, parseService.parse(list).get(1));
    }

    @Test
    public void parse_throwsRuntimeExceptionIfFetchedDataFormatWasIncorrect_notOk() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"33", "cows"});
        exception.expect(RuntimeException.class);
        exception.expectMessage("Input data doesn't correspond require format");
        parseService.parse(list);
    }

    @Test
    public void parse_throwsRuntimeExceptionIfFetchedOperationWasIncorrect_notOk() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"k","apricots", "100"});
        exception.expect(RuntimeException.class);
        exception.expectMessage("Input data doesn't correspond require format");
        parseService.parse(list);
    }

    @Test
    public void parse_throwsRuntimeExceptionWithBlankArrays_notOk() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{});
        exception.expect(RuntimeException.class);
        exception.expectMessage("Input data doesn't correspond require format");
        parseService.parse(list);
    }
}
