package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String TITLE = "type,fruit,quantity";
    private static TransactionParserService transactionParserService;
    private static final List<String> VALID_LIST = List.of(TITLE,
            "b,apple,20",
            "p,banana,10",
            "r,apple,100",
            "s,banana,90");
    private static final List<String> INVALID_LIST_WITH_WRONG_OPERATION = List.of(TITLE,
            "a,apple,20",
            "b,banana,19");
    private static final List<String> INVALID_LIST_WITH_WRONG_QUANTITY = List.of(TITLE,
            "s,apple,20",
            "b,banana,-3");

    private static final List<String> INVALID_LIST_WITH_WRONG_RECORD_LENGTH = List.of(TITLE,
            "s,apple,20",
            "b,banana");

    @BeforeAll
    static void beforeAll() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test
    void parse_validList_Ok() {
        List<FruitTransaction> expected = List.of(
                createFruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 20),
                createFruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 10),
                createFruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 100),
                createFruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 90)
        );
        List<FruitTransaction> actual = transactionParserService.parse(VALID_LIST);
        assertIterableEquals(expected, actual);
    }

    @Test
    void parse_invalidListWithWrongOperation_NotOk() {
        assertThrows(RuntimeException.class, () ->
                transactionParserService.parse(INVALID_LIST_WITH_WRONG_OPERATION));
    }

    @Test
    void parse_invalidListWithWrongQuantity_NotOk() {
        assertThrows(RuntimeException.class, () ->
                transactionParserService.parse(INVALID_LIST_WITH_WRONG_QUANTITY));
    }

    @Test
    void parse_invalidListWithWrongRecordLength() {
        assertThrows(IllegalArgumentException.class, () ->
                transactionParserService.parse(INVALID_LIST_WITH_WRONG_RECORD_LENGTH));
    }

    private FruitTransaction createFruitTransaction(FruitTransaction.Operation operation,
                                                    String fruit,
                                                    int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
