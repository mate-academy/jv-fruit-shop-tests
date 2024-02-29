package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.ArticleDao;
import core.basesyntax.dao.ArticleDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final String LINE_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private final ArticleDao articleDao = new ArticleDaoImpl();
    private final TransactionService transactionService
            = new FruitTransactionServiceImpl(articleDao);
    private final TransactionParser transactionParser
            = new TransactionParserImpl(transactionService);

    @BeforeEach
    public void beforeEach() {
        Storage.storage.put("apple", 0);
        Storage.storage.put("banana", 0);
        Storage.storage.put("orange", 0);
    }

    @AfterEach
    public void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void constructor_parameterIsNull_notOk() {
        TransactionService nullTransactionService = null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                 new TransactionParserImpl(nullTransactionService));
        assertEquals("Constructor parameter can't be null", exception.getMessage());
    }

    @Test
    void parse_correctLines_ok() {
        List<String> lines = List.of(
                   "b,apple,20",
                            "s,apple,45",
                            "p,apple,1",
                            "r,apple,0",
                            "b,banana,10000",
                            "s,banana,46",
                            "p,banana,34",
                            "r,banana,4",
                            "b,orange,441",
                            "s,orange,256",
                            "p,orange,12",
                            "r,orange,98");
        List<FruitTransaction> fruitTransactionsListResult = transactionParser.parse(lines);
        List<FruitTransaction> expectedFruitTransactionList = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        for (String line : lines) {
            fruitTransaction.setFruit(line.split(LINE_SEPARATOR)[FRUIT_INDEX]);
            fruitTransaction.setQuantity(Integer.parseInt(line
                    .split(LINE_SEPARATOR)[QUANTITY_INDEX]));
            for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
                if (operation.getCode().equals(line.split(LINE_SEPARATOR)[OPERATION_INDEX])) {
                    fruitTransaction.setOperation(operation);
                }
            }
            expectedFruitTransactionList.add(fruitTransaction);
        }

        assertEquals(lines.size(), fruitTransactionsListResult.size());
        assertTrue(fruitTransactionsListResult.containsAll(expectedFruitTransactionList));
    }

    @Test
    void parse_emptyList_notOk() {
        List<String> emptyList = new ArrayList<>();
        Throwable exception = assertThrows(RuntimeException.class,
                () -> transactionParser.parse(emptyList));
        assertEquals("""
                List '%s' is empty"""
                .formatted(emptyList), exception.getMessage());
    }

    @Test
    void parse_parameterIsNull_notOk() {
        List<String> nullList = null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parse(nullList));
        assertEquals("Parameter can't be null", exception.getMessage());
    }
}
