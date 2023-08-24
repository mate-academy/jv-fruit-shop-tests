package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static final int TEST_QUANTITY = 10;
    private static final char FIRST_TEST_CHARACTER = 'a';
    private static final char LAST_TEST_CHARACTER = 'z';
    private static final String COMA_DELIMITER = ",";
    private static final String FRUIT_NAME = "fruitName";
    private static final List<String> dataStringList = new ArrayList<>();
    private static final String DATA_FILE_TITLE = "type,fruit,quantity";
    private static ParseService parseService;
    private final String[] correctCodes = {"b", "s", "p", "r"};

    @BeforeAll
    static void initVariables() {
        parseService = new ParseServiceImpl();
    }

    @BeforeEach
    void setLists() {
        dataStringList.clear();
        dataStringList.add(DATA_FILE_TITLE);
    }

    @Test
    void parseTransaction_nullDataStringList_notOk() {
        assertThrows(NullPointerException.class, () -> parseService.parseTransaction(null));
    }

    @Test
    void parseTransaction_correctOperationCode_ok() {
        for (String code : correctCodes) {
            dataStringList.add(code + COMA_DELIMITER + FRUIT_NAME + COMA_DELIMITER + TEST_QUANTITY);

            List<FruitTransaction> transactionList = parseService.parseTransaction(dataStringList);
            Operation actualOperation = transactionList.get(0).getOperation();
            Operation expectedOperation = Operation.getByCode(code);

            assertEquals(expectedOperation, actualOperation);

            setLists();
        }
    }

    @Test
    void parseTransaction_incorrectOperationCode_notOk() {
        for (int i = FIRST_TEST_CHARACTER; i <= LAST_TEST_CHARACTER; i++) {
            if (List.of(correctCodes).contains(String.valueOf((char) i))) {
                continue;
            }
            dataStringList.add((char) i
                    + COMA_DELIMITER
                    + FRUIT_NAME
                    + COMA_DELIMITER
                    + TEST_QUANTITY);

            assertThrows(RuntimeException.class,
                    () -> parseService.parseTransaction(dataStringList));

            setLists();
        }
    }
}
