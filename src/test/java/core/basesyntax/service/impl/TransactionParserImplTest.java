package core.basesyntax.service.impl;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private final ArticleDao articleDao = new ArticleDaoImpl();
    private final TransactionService transactionService
            = new FruitTransactionServiceImpl(articleDao);
    private final TransactionParser transactionParser
            = new TransactionParserImpl(transactionService);

    @BeforeEach
    public void beforeEach() {
        String apple = "apple";
        String banana = "banana";
        String orange = "orange";
        Storage.storage.put(apple, 0);
        Storage.storage.put(banana, 0);
        Storage.storage.put(orange, 0);
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
        assertEquals(lines.size(), fruitTransactionsList.size());
    }

    @Test
    void parse_emptyList_notOk() {
        List<String> emptyList = new ArrayList<>();
        Throwable exception = assertThrows(RuntimeException.class,
                () -> transactionParser.parse(emptyList));
        assertEquals("List '" + emptyList + "' is empty", exception.getMessage());
    }

    @Test
    void parse_parameterIsNull_notOk() {
        List<String> nullList = null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parse(nullList));
        assertEquals("Parameter can't be null", exception.getMessage());
    }
}
