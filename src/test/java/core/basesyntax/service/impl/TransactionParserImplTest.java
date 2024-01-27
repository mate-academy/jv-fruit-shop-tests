package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ArticleDao;
import core.basesyntax.dao.ArticleDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private ArticleDao articleDao = new ArticleDaoImpl();
    private TransactionService transactionService = new FruitTransactionServiceImpl(articleDao);
    private TransactionParser transactionParser = new TransactionParserImpl(transactionService);

    @BeforeAll
    public static void init() {
        String apple = "apple";
        String banana = "banana";
        String orange = "orange";
        Storage.storage.put(apple, 0);
        Storage.storage.put(banana, 0);
        Storage.storage.put(orange, 0);
    }

    @Test
    void constructor_parameterIsNull_NotOk() {
        TransactionService nullTransactionService = null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                 new TransactionParserImpl(nullTransactionService));
        assertEquals("Constructor parameter can't be null", exception.getMessage());
    }

    @Test
    void parse_correctLines_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("b,apple,20");
        lines.add("s,apple,45");
        lines.add("p,apple,1");
        lines.add("r,apple,0");
        lines.add("b,banana,10000");
        lines.add("s,banana,46");
        lines.add("p,banana,34");
        lines.add("r,banana,4");
        lines.add("b,orange,441");
        lines.add("s,orange,256");
        lines.add("p,orange,12");
        lines.add("r,orange,98");
        List<FruitTransaction> fruitTransactionsList = transactionParser.parse(lines);
        assertTrue(lines.size() == fruitTransactionsList.size());
    }

    @Test
    void parse_emptyList_NotOk() {
        List<String> emptyList = new ArrayList<>();
        Throwable exception = assertThrows(RuntimeException.class, () ->
                transactionParser.parse(emptyList));
        assertEquals("List '" + emptyList + "' is empty", exception.getMessage());
    }

    @Test
    void parse_parameterIsNull_NotOk() {
        List<String> nullList = null;
        Throwable exception = assertThrows(NullPointerException.class, () ->
                transactionParser.parse(nullList));
        assertEquals("Parameter can't be null", exception.getMessage());
    }
}
