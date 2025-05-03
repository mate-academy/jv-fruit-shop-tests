package core.basesyntax.service.implemantation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserImplTest {
    private static final String LINE_HEADER = "type,fruit,quantity";
    private static final String FIRST_LINE = "b,banana,20";
    private static final String SECOND_LINE = "b,apple,100";
    private static final int SIZE_PARSE_OK = 2;
    private static final int SIZE_PARSE_NOT_OK = 0;
    private static final int INDEX_FIRST_FRUIT_TRANSACTION = 0;
    private static final int EXPECTED_QUANTITY = 20;
    private static final String EXPECTED_FRUIT_NAME = "banana";
    private static final Operation EXPECTED_OPERATION = Operation.BALANCE;
    private static DataParser dataParser;

    @BeforeAll
    static void beforeAll() {
        dataParser = new DataParserImpl();
    }

    @Test
    void parse_validData_ok() {
        List<String> lines = new ArrayList<>();
        lines.add(LINE_HEADER);
        lines.add(FIRST_LINE);
        lines.add(SECOND_LINE);

        List<FruitTransaction> transactions = dataParser.parse(lines);
        FruitTransaction fruitTransaction = transactions.get(INDEX_FIRST_FRUIT_TRANSACTION);

        assertEquals(SIZE_PARSE_OK, transactions.size());
        assertEquals(EXPECTED_QUANTITY, fruitTransaction.getQuantity());
        assertEquals(EXPECTED_FRUIT_NAME, fruitTransaction.getFruit());
        assertEquals(EXPECTED_OPERATION, fruitTransaction.getOperation());
    }

    @Test
    void parse_onlyHeader_ok() {
        List<String> lines = new ArrayList<>();
        lines.add(LINE_HEADER);

        List<FruitTransaction> transactions = dataParser.parse(lines);

        assertEquals(SIZE_PARSE_NOT_OK, transactions.size());
    }

    @Test
    void parse_emptyList_ok() {
        List<String> lines = new ArrayList<>();
        List<FruitTransaction> transactions = dataParser.parse(lines);

        assertEquals(SIZE_PARSE_NOT_OK, transactions.size());
    }
}
