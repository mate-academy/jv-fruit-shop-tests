package core.basesyntax.data.processing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final String BALANCE_OPERATION = "b,banana,20";
    private static final String SUPPLY_OPERATION = "s,banana,100";
    private static final String RETURN_OPERATION = "r,banana,10";
    private static final String PURCHASE_OPERATION = "p,banana,5";
    private static TransactionParser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new TransactionParserImpl();
    }

    @Test
    void parseTransactions_WriteList_ok() {
        List<String> lines = List.of(BALANCE_OPERATION,
                SUPPLY_OPERATION,
                RETURN_OPERATION,
                PURCHASE_OPERATION);
        int expected = lines.size();
        assertEquals(expected, parser.parseTransactions(lines).size());
        List<FruitTransaction> fruitInfo = parser.parseTransactions(lines);
        assertEquals("b", fruitInfo.get(0).getOperation().getCode());
        assertEquals("banana", fruitInfo.get(0).getFruit());
        assertEquals(20, fruitInfo.get(0).getQuantity());
    }
}
