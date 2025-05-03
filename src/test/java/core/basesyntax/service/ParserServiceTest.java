package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserServiceTest {
    private static ParserService parserService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_nullInput_notOk() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Input param is null.");
        parserService.parse(null);
    }

    @Test
    public void parse_badDataInput_notOk() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Bad data in line: 2");
        parserService.parse(List.of("b,fruit,1", "b,,1"));
    }

    @Test
    public void parse_dataInput_Ok() {
        List<String> data = List.of("b,apple,10", "b,cherry,15");
        List<FruitTransaction> actualTransactions = parserService.parse(data);
        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10));
        expectedTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "cherry", 15));

        for (int i = 0; i < expectedTransactions.size(); i++) {
            assertEquals(expectedTransactions.get(i), actualTransactions.get(i));
        }
    }
}
