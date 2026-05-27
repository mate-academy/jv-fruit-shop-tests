package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;
import core.basesyntax.service.impl.DataParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataParserTest {
    private static final String HEADER = "type,fruit,quantity";

    private static final List<String> TEST_DATA = new ArrayList<String>();
    private static final String CORRECT = "b,apple,10";

    private static final FruitTransaction CORRECT_TRANSACTION = new FruitTransaction();
    private static final FruitTransaction.Operation CORRECT_TRANS_OP
            = FruitTransaction.Operation.BALANCE;
    private static final String CORRECT_TRANS_NM = "apple";
    private static final int CORRECT_TRANS_QTITY = 10;

    private static final String WRONG_TYPE = "w,apple,10";
    private static final String WRONG_QTITY_TYPE = "b,apple,qwe";

    private static final String EMPTY_STRING = "";
    private static final String EMPTY_TYPE = ",apple,10";
    private static final String EMPTY_FRUIT = "b,,10";
    private static final String EMPTY_QUANTITY = "b,apple,";

    private final DataParser dataParser = new DataParserImpl();

    @BeforeEach
    void setup() {
        Storage.getFruits().clear();
        TEST_DATA.clear();

        TEST_DATA.add(HEADER);

        CORRECT_TRANSACTION.setOperation(CORRECT_TRANS_OP);
        CORRECT_TRANSACTION.setName(CORRECT_TRANS_NM);
        CORRECT_TRANSACTION.setQuantity(CORRECT_TRANS_QTITY);
    }

    @Test
    void parseTransactions_correctData_ok() {
        TEST_DATA.add(CORRECT);

        List<FruitTransaction> transactions = dataParser.parseTransactions(TEST_DATA);

        assertEquals(1, transactions.size());
        assertEquals(CORRECT_TRANS_OP, transactions.get(0).getOperation());
        assertEquals(CORRECT_TRANS_NM, transactions.get(0).getName());
        assertEquals(CORRECT_TRANS_QTITY, transactions.get(0).getQuantity());
    }

    @Test
    void parseTransactions_wrongType_notOk() {
        TEST_DATA.add(WRONG_TYPE);

        assertThrows(RuntimeException.class,
                () -> dataParser.parseTransactions(TEST_DATA));
    }

    @Test
    void parseTransactions_wrongQuantityType_notOk() {
        TEST_DATA.add(WRONG_QTITY_TYPE);

        assertThrows(RuntimeException.class,
                () -> dataParser.parseTransactions(TEST_DATA));
    }

    @Test
    void parseTransactions_emptyString_notOk() {
        TEST_DATA.add(EMPTY_STRING);

        assertThrows(RuntimeException.class,
                () -> dataParser.parseTransactions(TEST_DATA));
    }

    @Test
    void parseTransactions_emptyType_notOk() {
        TEST_DATA.add(EMPTY_TYPE);

        assertThrows(RuntimeException.class,
                () -> dataParser.parseTransactions(TEST_DATA));
    }

    @Test
    void parseTransactions_emptyFruit_notOk() {
        TEST_DATA.add(EMPTY_FRUIT);

        assertThrows(RuntimeException.class,
                () -> dataParser.parseTransactions(TEST_DATA));
    }

    @Test
    void parseTransactions_emptyQuantity_notOk() {
        TEST_DATA.add(EMPTY_QUANTITY);

        assertThrows(RuntimeException.class,
                () -> dataParser.parseTransactions(TEST_DATA));
    }

    @Test
    void parseTransactions_wongHeadPos_notOk() {
        TEST_DATA.add(0, EMPTY_QUANTITY);

        assertThrows(RuntimeException.class,
                () -> dataParser.parseTransactions(TEST_DATA));
    }
}
