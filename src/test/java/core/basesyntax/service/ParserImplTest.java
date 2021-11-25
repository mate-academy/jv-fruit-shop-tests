package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser PARSER = new ParserImpl(new ValidatorImpl());

    @Test
    public void parse_Ok() {
        List<String> data = new ArrayList<>();
        data.addAll(List.of("type,fruit,quantity", "b,banana,15"));
        TransactionDto transaction
                = new TransactionDto("b", new Fruit("banana"), 15);
        List<TransactionDto> expected = List.of(transaction);
        List<TransactionDto> actual = PARSER.parse(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseWithManyLines_Ok() {
        List<String> data = new ArrayList<>();
        data.addAll(List.of("b,banana,15", "b,apple,15",
                "p,banana,15", "p,apple,15"));
        TransactionDto transaction1
                = new TransactionDto("b", new Fruit("apple"), 15);
        TransactionDto transaction2
                = new TransactionDto("p", new Fruit("banana"), 15);
        TransactionDto transaction3
                = new TransactionDto("p", new Fruit("apple"), 15);
        List<TransactionDto> expected = List.of(transaction1, transaction2, transaction3);
        List<TransactionDto> actual = PARSER.parse(data);
        Assert.assertEquals(expected, actual);
    }
}
