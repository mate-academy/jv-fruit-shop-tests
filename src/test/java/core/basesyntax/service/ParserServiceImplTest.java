package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final List<String> DEFAULT_INPUT = List.of(
            "type,fruit,quantity",
            "b,banana,45",
            "p,apple,42");
    private static final List<FruitTransaction> DEFAULT_OUTPUT = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 45),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 42));
    private static FileParserService fileParserService;

    @BeforeClass
    public static void beforeClass() {
        fileParserService = new ParserServiceImpl();
    }

    @Test
    public void parserServiceImpl_getListTransactionFromDefaultInput_OK() {
        List<FruitTransaction> actual = fileParserService.getFruitTransaction(DEFAULT_INPUT);
        List<FruitTransaction> expected = new ArrayList<>(DEFAULT_OUTPUT);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parserServiceImpl_parseNull_OK() {
        fileParserService.getFruitTransaction(null);
    }
}
