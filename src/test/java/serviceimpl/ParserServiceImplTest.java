package serviceimpl;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ParserService;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        List<String> input = new ArrayList<>();
        input.add("type,fruits,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        List<FruitTransaction> actual = parserService.parse(input);
        Assert.assertEquals(expected,actual);
    }
}
