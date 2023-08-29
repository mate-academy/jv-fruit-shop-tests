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
    private static final int TEST_QUANTITY = 77;
    private static final String COMA_DELIMITER = ",";
    private static final String FRUIT_NAME = "fruitName";
    private static final List<String> dataStringList = new ArrayList<>();
    private static final String DATA_FILE_TITLE = "type,fruit,quantity";
    private static ParseService parseService;
    private static final List<FruitTransaction> expectedTransactionList = new ArrayList<>();
    private final String[] correctCodes = {"b", "s", "p", "r"};
    private final String[] incorrectCodes =
            {"A", "a", "B", "S", "P", "Q", "q", "Y", "y", ";", ".", "/", ":", "!", "&"};

    @BeforeAll
    static void initVariables() {
        parseService = new ParseServiceImpl();

        FruitTransaction correctTransactionBalance = new FruitTransaction();
        correctTransactionBalance.setOperation(Operation.BALANCE);
        correctTransactionBalance.setFruitName(FRUIT_NAME);
        correctTransactionBalance.setQuantity(TEST_QUANTITY);

        FruitTransaction correctTransactionSupply = new FruitTransaction();
        correctTransactionSupply.setOperation(Operation.SUPPLY);
        correctTransactionSupply.setFruitName(FRUIT_NAME);
        correctTransactionSupply.setQuantity(TEST_QUANTITY);

        FruitTransaction correctTransactionPurchase = new FruitTransaction();
        correctTransactionPurchase.setOperation(Operation.PURCHASE);
        correctTransactionPurchase.setFruitName(FRUIT_NAME);
        correctTransactionPurchase.setQuantity(TEST_QUANTITY);

        FruitTransaction correctTransactionReturn = new FruitTransaction();
        correctTransactionReturn.setOperation(Operation.RETURN);
        correctTransactionReturn.setFruitName(FRUIT_NAME);
        correctTransactionReturn.setQuantity(TEST_QUANTITY);

        expectedTransactionList.add(correctTransactionBalance);
        expectedTransactionList.add(correctTransactionSupply);
        expectedTransactionList.add(correctTransactionPurchase);
        expectedTransactionList.add(correctTransactionReturn);
    }

    @BeforeEach
    void setLists() {
        dataStringList.clear();
        dataStringList.add(DATA_FILE_TITLE);
    }

    @Test
    void parseTransaction_correctOperationCode_ok() {
        dataStringList.add(correctCodes[0] + COMA_DELIMITER
                + FRUIT_NAME + COMA_DELIMITER + TEST_QUANTITY);
        dataStringList.add(correctCodes[1]
                + COMA_DELIMITER + FRUIT_NAME + COMA_DELIMITER + TEST_QUANTITY);
        dataStringList.add(correctCodes[2]
                + COMA_DELIMITER + FRUIT_NAME + COMA_DELIMITER + TEST_QUANTITY);
        dataStringList.add(correctCodes[3]
                + COMA_DELIMITER + FRUIT_NAME + COMA_DELIMITER + TEST_QUANTITY);

        List<FruitTransaction> actualTransactionList =
                parseService.parseTransaction(dataStringList);

        assertEquals(expectedTransactionList, actualTransactionList);
    }

    @Test
    void parseTransaction_incorrectOperationCode_notOk() {
        for (String incorrectCode : incorrectCodes) {
            dataStringList.add(incorrectCode + COMA_DELIMITER
                    + FRUIT_NAME + COMA_DELIMITER + TEST_QUANTITY);

            assertThrows(RuntimeException.class,
                    () -> parseService.parseTransaction(dataStringList),
                    "Test failed with value \"" + incorrectCode + "\"");

            setLists();
        }
    }
}
