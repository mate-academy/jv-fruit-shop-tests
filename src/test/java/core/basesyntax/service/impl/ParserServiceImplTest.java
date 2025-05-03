package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;
    private static List<String> input;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
        input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
    }

    @Test
    public void parseLines_rightInput_ok() {
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto("b", "banana", 20));
        expected.add(new TransactionDto("b","apple", 100));
        List<TransactionDto> actual = parserService.parseLines(input);
        Assert.assertNotNull(actual);
        Assert.assertEquals("Expected " + expected + ", but actual " + actual, expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void parseLines_nullInput_notOk() {
        parserService.parseLines(null);
    }

    @Test
    public void parseLines_emptyInput_ok() {
        List<TransactionDto> expected = Collections.emptyList();
        List<TransactionDto> actual = parserService.parseLines(Collections.emptyList());
        Assert.assertNotNull(actual);
        Assert.assertEquals("Expected " + expected + ", but actual " + actual, expected, actual);
    }
}
