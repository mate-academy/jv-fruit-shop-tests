import static org.junit.Assert.fail;

import fruitshop.dao.FruitShopStorageDaoImpl;
import fruitshop.db.FruitShopStorage;
import fruitshop.service.impl.TransactionRowParserImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class TestTransactionRowParser {
    private static final FruitShopStorageDaoImpl DAO = new FruitShopStorageDaoImpl();
    private static final TransactionRowParserImpl TRANSACTION_ROW_PARSER =
            new TransactionRowParserImpl(DAO);
    private static final List<String> VALID_DATA_ROWS = List.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );
    private static final int INDEX_TO_PUT_IN_LIST_MIDDLE_FIRST = 2;
    private static final int INDEX_TO_PUT_IN_LIST_MIDDLE_SECOND = 6;
    private static final int INDEX_TO_PUT_IN_LIST_MIDDLE_THIRD = 8;
    private static final int INDEX_TO_PUT_IN_LIST_START = 0;
    private static final int INDEX_TO_PUT_IN_LIST_AFTER_BALANCE = 3;
    private static final String TEST_FRUIT_AND_COUNT_UNFINISHED_ROW = "apple,32";
    private List<String> currentDataRows;

    @Before
    public void setUp() {
        currentDataRows = new ArrayList<>(VALID_DATA_ROWS);
        FruitShopStorage.fruitTransactions.clear();
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectColumnSizeInList_emptyRow_notOk() {
        currentDataRows.add(INDEX_TO_PUT_IN_LIST_MIDDLE_FIRST, "");
        TRANSACTION_ROW_PARSER.parse(currentDataRows);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectColumnSizeInList_less_notOk() {
        currentDataRows.add(INDEX_TO_PUT_IN_LIST_MIDDLE_SECOND,
                TEST_FRUIT_AND_COUNT_UNFINISHED_ROW);
        TRANSACTION_ROW_PARSER.parse(currentDataRows);
    }

    @Test(expected = RuntimeException.class)
public void parse_incorrectColumnSizeInList_extraColumn_notOk() {
        currentDataRows.add(INDEX_TO_PUT_IN_LIST_MIDDLE_THIRD,
                "p," + TEST_FRUIT_AND_COUNT_UNFINISHED_ROW + ",extra-column"
        );
        TRANSACTION_ROW_PARSER.parse(currentDataRows);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectOperationLength_bigger_notOk() {
        currentDataRows.add(INDEX_TO_PUT_IN_LIST_MIDDLE_THIRD,
                "purchase," + TEST_FRUIT_AND_COUNT_UNFINISHED_ROW
        );
        TRANSACTION_ROW_PARSER.parse(currentDataRows);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectOperationLength_empty_notOk() {
        currentDataRows.add(INDEX_TO_PUT_IN_LIST_MIDDLE_THIRD,
                "," + TEST_FRUIT_AND_COUNT_UNFINISHED_ROW
        );
        TRANSACTION_ROW_PARSER.parse(currentDataRows);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongOperationOrder_firstOperationIsNotBalance_notOk() {
        currentDataRows.add(INDEX_TO_PUT_IN_LIST_START,
                "s," + TEST_FRUIT_AND_COUNT_UNFINISHED_ROW
        );
        TRANSACTION_ROW_PARSER.parse(currentDataRows);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongOperationOrder_balanceInMiddleAfterAlreadyBalanced_notOk() {
        int index = INDEX_TO_PUT_IN_LIST_AFTER_BALANCE + INDEX_TO_PUT_IN_LIST_MIDDLE_FIRST;
        currentDataRows.add(index,
                "b," + TEST_FRUIT_AND_COUNT_UNFINISHED_ROW
        );
        TRANSACTION_ROW_PARSER.parse(currentDataRows);
    }

    @Test()
    public void parse_everythingOk_toBeOk() {
        try {
            TRANSACTION_ROW_PARSER.parse(currentDataRows);
        } catch (Exception e) {
            fail("Expected to be ok, but got " + e
                    + '\n' + getStackTraceString(e));
        }
    }

    private static String getStackTraceString(Exception e) {
        return Arrays
                .stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
    }
}
