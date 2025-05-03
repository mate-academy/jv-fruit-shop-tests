package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.impl.ParserServiceImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeAll
    public static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parseTransaction_withNull_notOk() {
        assertThrows(NullPointerException.class, () -> parserService.parseTransactions(null));
    }

    @Test
    void parseTransaction_withEmptyArgument_Ok() {
        List<String> argument = Collections.emptyList();
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = parserService.parseTransactions(argument);
        assertEquals(expected, actual);
    }

    @Test
    public void parserService_successfulTransaction_ok() {
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("b,apple,5");
        transactionStrings.add("s,orange,1");
        parserService.parseTransactions(transactionStrings);
    }
}
