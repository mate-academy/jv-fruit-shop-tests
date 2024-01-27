package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ArticleDao;
import core.basesyntax.dao.ArticleDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static final String LINE_SEPARATOR = ",";
    private static final int TRANSACTION_FIELD_INDEX = 0;
    private static final int ARTICLE_FIELD_INDEX = 1;
    private static final int QUANTITY_FIELD_INDEX = 2;
    private static final List<String> LINES = new ArrayList<>();
    private ArticleDao articleDao = new ArticleDaoImpl();
    private TransactionService fruitTRansactionService
            = new FruitTransactionServiceImpl(articleDao);

    @BeforeAll
    public static void init() {
        String apple = "apple";
        String banana = "banana";
        String orange = "orange";
        Storage.storage.put(apple, 0);
        Storage.storage.put(banana, 0);
        Storage.storage.put(orange, 0);
    }

    @AfterEach
    void tearDown() {
        LINES.clear();
    }

    @Test
    void constructor_parameterIsNull_NotOk() {
        ArticleDao nullArticleDao = null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new FruitTransactionServiceImpl(nullArticleDao));
        assertEquals("Constructor parameter can't be null", exception.getMessage());
    }

    @Test
    void create_correctLineFormat_ok() {
        LINES.add("b,apple,20");
        LINES.add("s,apple,45");
        LINES.add("p,apple,1");
        LINES.add("r,apple,0");
        LINES.add("b,banana,10000");
        LINES.add("s,banana,46");
        LINES.add("p,banana,34");
        LINES.add("r,banana,4");
        LINES.add("b,orange,441");
        LINES.add("s,orange,256");
        LINES.add("p,orange,12");
        LINES.add("r,orange,98");
        for (String line : LINES) {
            String expectedTransactionCodeField
                    = line.split(LINE_SEPARATOR)[TRANSACTION_FIELD_INDEX];
            String expectedArticleField
                    = line.split(LINE_SEPARATOR)[ARTICLE_FIELD_INDEX];
            int expectedQuantityField
                    = Integer.parseInt(line.split(LINE_SEPARATOR)[QUANTITY_FIELD_INDEX]);
            FruitTransaction transaction
                    = fruitTRansactionService.createTransaction(line);
            assertEquals(expectedQuantityField, transaction.getQuantity());
            assertEquals(expectedArticleField, transaction.getFruit());
            assertEquals(expectedTransactionCodeField, transaction.getOperation().getCode());
        }
    }

    @Test
    void createTransaction_incorrectArticleNameFormat_NotOk() {
        LINES.add("b,applE,20");
        LINES.add("s,ApPle|(,45");
        LINES.add("p,APPLE,1");
        LINES.add("r,ApplE,0");
        LINES.add("b,Banana,10000");
        LINES.add("s,baNAna^*,46");
        LINES.add("p,bnana#$,34");
        LINES.add("r,Banana,4");
        LINES.add("b,ORange!,441");
        LINES.add("s,Orange,256");
        LINES.add("p,ORANGE,12");
        LINES.add("r,$%&OraNge,98");
        for (String line : LINES) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    fruitTRansactionService.createTransaction(line));
            assertEquals("Article name in line: '" + line
                            + "' shouldn't contain numbers and"
                            + " special characters",
                    exception.getMessage());
        }
    }

    @Test
    void createTransaction_quantityIs0_Ok() {
        LINES.add("b,apple,0");
        LINES.add("s,apple,0");
        LINES.add("p,apple,0");
        LINES.add("r,apple,0");
        LINES.add("b,banana,0");
        LINES.add("s,banana,0");
        LINES.add("p,banana,0");
        LINES.add("r,banana,0");
        LINES.add("b,orange,0");
        LINES.add("s,orange,0");
        LINES.add("p,orange,0");
        LINES.add("r,orange,0");
        int expectedQuantityField = 0;
        for (String line : LINES) {
            FruitTransaction transaction = fruitTRansactionService.createTransaction(line);
            assertEquals(expectedQuantityField, transaction.getQuantity());
        }
    }

    @Test
    void createTransaction_parameterIsNull_NotOK() {
        LINES.add(null);
        LINES.add(null);
        LINES.add(null);
        LINES.add(null);
        for (String line : LINES) {
            Throwable exception = assertThrows(NullPointerException.class, () ->
                    fruitTRansactionService.createTransaction(line));
            assertEquals("Parameter can't be null", exception.getMessage());
        }
    }

    @Test
    void createTransaction_lineIsEmpty_NotOk() {
        LINES.add("");
        LINES.add("");
        LINES.add("");
        LINES.add("");
        for (String line : LINES) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    fruitTRansactionService.createTransaction(line));
            assertEquals("Line is empty", exception.getMessage());
        }
    }

    @Test
    void createTransaction_incorrectLineFormat() {
        LINES.add("banana,b100");
        LINES.add("Apple");
        LINES.add("b,Apple100");
        LINES.add("bapple,100");
        LINES.add("bApple100");
        LINES.add("r,Orange,100,100");
        LINES.add("r,orange,orange,100");
        LINES.add("r,Orange,Orange,,Apple,100");
        LINES.add("s,s,Orange,,Apple,100");
        LINES.add("s,s,Orange,21,Apple,,");
        LINES.add(",Banana,100");
        LINES.add(",banana,");
        LINES.add(",,40");
        LINES.add(",,1");
        LINES.add("r,Apple,");
        LINES.add(",apple,");
        LINES.add("r,,70");
        LINES.add(",,");
        for (String line : LINES) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    fruitTRansactionService.createTransaction(line));
            assertEquals("Wrong format in line: '" + line
                    + "'\nShould be 3 fields separated by a coma. "
                    + "Example: 'transaction,fruit,quantity'", exception.getMessage());
        }
    }

    @Test
    void createTransaction_articleDoesntExistInDatabase_NotOk() {
        LINES.add("b,pineapple,101");
        LINES.add("r,pineapple,40");
        LINES.add("s,pineapple,5");
        LINES.add("p,pineapple,7");
        LINES.add("b,strawberry,104");
        LINES.add("r,strawberry,20");
        LINES.add("s,strawberry,51");
        LINES.add("p,strawberry,37");
        LINES.add("b,lemon,37");
        LINES.add("s,lemon,37");
        LINES.add("p,lemon,37");
        LINES.add("r,lemon,37");
        for (String line : LINES) {
            String article = line.split(LINE_SEPARATOR)[ARTICLE_FIELD_INDEX];
            Throwable exception = assertThrows(RuntimeException.class,
                    () -> fruitTRansactionService.createTransaction(line));
            assertEquals("Storage doesn't contain article '" + article
                    + "'", exception.getMessage());
        }
    }

    @Test
    void createTransaction_incorrectTransactionIndex_NotOk() {
        LINES.add("h,apple,20");
        LINES.add("v,apple,20");
        LINES.add("d,apple,20");
        LINES.add("a,orange,441");
        LINES.add("t,orange,256");
        LINES.add("x,orange,12");
        LINES.add("o,banana,4");
        LINES.add("l,banana,4");
        LINES.add("m,banana,4");
        for (String line : LINES) {
            String transactionIndex
                    = line.split(LINE_SEPARATOR)[TRANSACTION_FIELD_INDEX];
            Throwable exception = assertThrows(RuntimeException.class,
                    () -> fruitTRansactionService.createTransaction(line));
            assertEquals("Incorrect transaction index '"
                            + transactionIndex + "' in line: '" + line + "'",
                    exception.getMessage());
        }
    }

    @Test
    void createTransaction_negativeQuantity_NotOk() {
        LINES.add("b,apple,-2");
        LINES.add("s,apple,-100");
        LINES.add("p,apple,-300");
        LINES.add("r,apple,-48");
        LINES.add("b,banana,-10000");
        LINES.add("s,banana,-46");
        LINES.add("p,banana,-34");
        LINES.add("r,banana,-4");
        LINES.add("b,orange,-441");
        LINES.add("s,orange,-256");
        LINES.add("p,orange,-12");
        LINES.add("r,orange,-98");
        for (String line : LINES) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    fruitTRansactionService.createTransaction(line));
            assertEquals("Quantity can't be less than zero in line: '"
                    + line + "'", exception.getMessage());
        }
    }

    @Test
    void createTransaction_quantityAdditionalCharacters_NotOk() {
        LINES.add("b,apple,one");
        LINES.add("s,apple,number");
        LINES.add("p,apple,cat");
        LINES.add("r,apple,1I1");
        LINES.add("b,banana,_.");
        LINES.add("s,banana,sixteen");
        LINES.add("p,banana,word");
        LINES.add("r,banana,one");
        LINES.add("b,orange,44.1");
        LINES.add("s,orange,2.56");
        LINES.add("p,orange,0.12");
        LINES.add("r,orange,six6");
        for (String line : LINES) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    fruitTRansactionService.createTransaction(line));
            assertEquals("Wrong format of quantity field in line: '" + line
                    + "'\n The field should be an integer number", exception.getMessage());
        }
    }

    @Test
    void createTransaction_spacesInLine_NotOk() {
        LINES.add("b ,apple,20");
        LINES.add(" s,apple,45");
        LINES.add("p  ,apple,1");
        LINES.add("r , apple, 0");
        LINES.add("b ,  banana,1 000 0");
        LINES.add("s,banana, 46");
        LINES.add("p, banana ,34");
        LINES.add("r ,banana , 4");
        LINES.add("b,orange,4 41 ");
        LINES.add("s,orange , 256");
        LINES.add(" p , o r a n g e , 1 2 ");
        LINES.add("r,ora nge,9 8");
        for (String line : LINES) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    fruitTRansactionService.createTransaction(line));
            assertEquals("Line '" + line
                    + "' shouldn't contain"
                    + " spaces and upper case letters",
                    exception.getMessage());
        }
    }
}
