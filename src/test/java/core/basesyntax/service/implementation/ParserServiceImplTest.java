package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private ParserService parserService;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        addAllFruitTransaction(expectedList);
        List<String> strings = new ArrayList<>();
        createListOfFruits(strings);
        List<FruitTransaction> actual = parserService.parse(strings);
        assertEquals(expectedList, actual);
    }

    @Test
    public void parseEmptyList() {
        List<FruitTransaction> actual = parserService.parse(Collections.emptyList());
        List<FruitTransaction> expected = new ArrayList<>();
        assertEquals(actual, expected);
    }

    public void addAllFruitTransaction(List<FruitTransaction> expectedList) {
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }

    public void createListOfFruits(List<String> strings) {
        strings.add("b,banana,20");
        strings.add("b,apple,100");
        strings.add("s,banana,100");
        strings.add("p,banana,13");
        strings.add("r,apple,10");
        strings.add("p,apple,20");
        strings.add("p,banana,5");
        strings.add("s,banana,50");
    }
}
