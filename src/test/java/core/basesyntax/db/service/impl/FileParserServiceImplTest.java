package core.basesyntax.db.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.model.FruitTransaction;
import core.basesyntax.db.service.FileParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileParserServiceImplTest {
    private static FileParserService fileParserService;

    @BeforeClass
    public static void setUp() {
        fileParserService = new FileParserServiceImpl();
    }

    @Test
    public void parse_validListDataType_ok() {
        List<String> test = new ArrayList<>(
                List.of("b,banana,130", "p,banana,15", "r,apple,20"));
        List<FruitTransaction> expected = new ArrayList<>(
                List.of(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 130),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15),
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20)));
        List<FruitTransaction> actual = fileParserService.parse(test);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_inputIsEmpty_ok() {
        List<String> test = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = fileParserService.parse(test);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_letterInUpperCase_ok() {
        List<String> test = new ArrayList<>(List.of("B,apple,20", "b,BANANA,10"));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10)));
        List<FruitTransaction> actual = fileParserService.parse(test);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_quantityIsNegative_ok() {
        List<String> test = new ArrayList<>(List.of("b,banana,-30"));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 30)));
        List<FruitTransaction> actual = fileParserService.parse(test);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void parse_inputIsNull_notOk() {
        List<String> test = new ArrayList<>();
        test.add(null);
        List<FruitTransaction> actual = fileParserService.parse(test);
    }
}
