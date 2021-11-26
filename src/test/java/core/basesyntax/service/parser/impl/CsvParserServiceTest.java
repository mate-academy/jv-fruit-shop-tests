package core.basesyntax.service.parser.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.operationhandler.Operation;
import core.basesyntax.service.parser.ParserService;
import core.basesyntax.service.validator.Validator;
import core.basesyntax.service.validator.impl.ValidatorImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvParserServiceTest {
    private static Validator validator;
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        parserService = new CsvParserService(validator);
    }

    @Test
    public void parse_validData_Ok() {
        List<String> dataFromFile = List.of("type,fruit,quantity", "b,apple,15", "p,apple,5");
        Transaction balanceApple = new Transaction(Operation.BALANCE, new Fruit("apple"), 15);
        Transaction purchaseApple = new Transaction(Operation.PURCHASE, new Fruit("apple"), 5);
        List<Transaction> expected = List.of(balanceApple, purchaseApple);
        List<Transaction> actual = parserService.parse(dataFromFile);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidData_notOk() {
        List<String> dataFromFile = List.of("type,fruit,quantity", ",apple,15", "p,apple,5");
        List<Transaction> actual = parserService.parse(dataFromFile);
    }
}
