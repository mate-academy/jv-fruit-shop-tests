package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {

    public static final int INDEX_FIRST = 0;
    public static final String TITLE = "type,fruit,quantity";
    private static final String BALANCE_OPERATION = "b,banana,20";
    private static final String SUPPLY_OPERATION = "s,banana,100";
    private static final String RETURN_OPERATION = "r,banana,10";
    private static final String PURCHASE_OPERATION = "p,banana,5";
    private static ParserService parserService;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parserData_writeList_ok() {
        List<String> inputData = List.of(TITLE, BALANCE_OPERATION,
                SUPPLY_OPERATION, RETURN_OPERATION, PURCHASE_OPERATION);
        int expected = inputData.size() - 1;
        assertEquals(expected, parserService.parserData(inputData).size());

        List<FruitTransaction> fruitTransactionList = parserService.parserData(inputData);
        assertEquals("b", fruitTransactionList.get(INDEX_FIRST).getOperation().getCode());
        assertEquals("banana", fruitTransactionList.get(INDEX_FIRST).getFruit());
        assertEquals(20, fruitTransactionList.get(INDEX_FIRST).getQuantity());
    }
}
