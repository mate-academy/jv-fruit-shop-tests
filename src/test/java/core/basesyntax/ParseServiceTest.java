package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.WrongDataBaseException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.implementations.ParseServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParseServiceTest {
    private static final ParseService parseService = new ParseServiceImpl();
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int TEST_BANANA_QUANTITY = 20;
    private static final int TEST_APPLE_QUANTITY = 20;
    private static final String BALANCE_OPERATION_CODE = "b";
    private static final String RETURN_OPERATION_CODE = "r";
    private static final String PURCHASE_OPERATION_CODE = "p";
    private static final String SUPPLY_OPERATION_CODE = "s";
    private static final String WRONG_ZAPP_OPERATION_CODE = "z";
    private static final String HEADER = "type,fruit,quantity";
    private static final String WRONG_HEADER = "i just wan`t to sleep";
    private static final String COMMA = ",";
    private static final String WRONG_QUANTITY_TYPE =
            "Someday You Gonna Realize You`ve Been sleepwalking through it all";
    private static List<String> testData;

    @BeforeEach
    void setUp() {
        testData = new ArrayList<>();
    }

    @Test
    void parseDataToTransaction_validFruitTransaction_okay() {
        testData.add(HEADER);
        testData.add(BALANCE_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        testData.add(PURCHASE_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        List<FruitTransaction> expected = new ArrayList<>();
        FruitTransaction.Operation balanceOperation =
                FruitTransaction.Operation.fromCode(BALANCE_OPERATION_CODE);
        FruitTransaction.Operation purchaseOperation =
                FruitTransaction.Operation.fromCode(PURCHASE_OPERATION_CODE);
        expected.add(new FruitTransaction(balanceOperation, BANANA, TEST_BANANA_QUANTITY));
        expected.add(new FruitTransaction(purchaseOperation, BANANA, TEST_BANANA_QUANTITY));
        List<FruitTransaction> transactions = parseService.parseDataToTransaction(testData);
        assertEquals(transactions, expected);
    }

    @Test
    void parseDataToTransaction_fruitTransactionEmpty_notOkay() {
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void parseDataToTransaction_fruitTransactionNull_notOkay() {
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(null));
    }

    @Test
    void parseDataToTransaction_missingQuantity_notOkay() {
        testData.add(HEADER);
        testData.add(BALANCE_OPERATION_CODE + COMMA + BANANA);
        testData.add(SUPPLY_OPERATION_CODE + COMMA + BANANA);
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void parseDataToTransaction_missingHeader_notOkay() {
        testData.add(BALANCE_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        testData.add(SUPPLY_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void parseDataToTransaction_invalidQuantity_notOkay() {
        testData.add(HEADER);
        testData.add(SUPPLY_OPERATION_CODE + COMMA + BANANA + COMMA + WRONG_QUANTITY_TYPE);
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void parseDataToTransaction_invalidOperation_notOkay() {
        testData.add(HEADER);
        testData.add(WRONG_ZAPP_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        testData.add(SUPPLY_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void parseDataToTransaction_invalidHeader_notOkay() {
        testData.add(WRONG_HEADER);
        testData.add(BALANCE_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        testData.add(SUPPLY_OPERATION_CODE + COMMA + BANANA + COMMA + TEST_BANANA_QUANTITY);
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }
}
