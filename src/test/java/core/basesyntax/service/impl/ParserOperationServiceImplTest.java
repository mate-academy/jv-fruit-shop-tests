package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.fruitscontent.FruitTransaction;
import core.basesyntax.service.ParserOperationService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParserOperationServiceImplTest {
    private static final List<String> DATA_FROM_FILE = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "s,banana,50");
    private ParserOperationService parser = new ParserOperationServiceImpl();

    @Test
    public void parserService_CorrectParse_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = parser.parseContentForOperations(DATA_FROM_FILE);
        assertEquals(expected, actual);
    }
}
