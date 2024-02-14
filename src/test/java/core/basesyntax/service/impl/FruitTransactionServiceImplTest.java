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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class FruitTransactionServiceImplTest {
    private static final String LINE_SEPARATOR = ",";
    private static final int TRANSACTION_FIELD_INDEX = 0;
    private static final int ARTICLE_FIELD_INDEX = 1;
    private static final int QUANTITY_FIELD_INDEX = 2;
    private static final List<String> LINES = new ArrayList<>();
    private final ArticleDao articleDao = new ArticleDaoImpl();
    private final TransactionService fruitTRansactionService
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
    void constructor_parameterIsNull_notOk() {
        ArticleDao nullArticleDao = null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new FruitTransactionServiceImpl(nullArticleDao));
        assertEquals("Constructor parameter can't be null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "b,apple,20", "p,banana,34",
            "s,apple,45", "r,banana,4",
            "p,apple,1", "b,orange,441",
            "r,apple,0", "s,orange,256",
            "b,banana,10000", "p,orange,12",
            "s,banana,46","r,orange,98"
    })
    void create_correctLineFormat_ok(String line) {
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

    @ParameterizedTest
    @ValueSource(strings = {
            "b,applE,20", "s,ApPle|(,45",
            "p,APPLE,1", "r,ApplE,0",
            "b,Banana,10000", "s,baNAna^*,46",
            "p,bnana#$,34", "r,Banana,4",
            "b,ORange!,441", "s,Orange,256",
            "p,ORANGE,12","r,$%&OraNge,98"
    })
    void createTransaction_incorrectArticleNameFormat_notOk(String line) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                fruitTRansactionService.createTransaction(line));
        assertEquals("Article name in line: '" + line
                        + "' shouldn't contain numbers and"
                        + " special characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "b,apple,0", "s,apple,0",
            "p,apple,0", "r,apple,0",
            "b,banana,0", "s,banana,0",
            "p,banana,0", "r,banana,0",
            "b,orange,0", "s,orange,0",
            "p,orange,0","r,orange,0"
    })
    void createTransaction_quantityIsZero_ok(String line) {
        int expectedQuantityField = 0;
        FruitTransaction transaction = fruitTRansactionService.createTransaction(line);
        assertEquals(expectedQuantityField, transaction.getQuantity());
    }

    @ParameterizedTest
    @NullSource
    void createTransaction_parameterIsNull_notOk(String line) {
        Throwable exception = assertThrows(NullPointerException.class, () ->
                fruitTRansactionService.createTransaction(line));
        assertEquals("Parameter can't be null", exception.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    void createTransaction_lineIsEmpty_notOk(String line) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                fruitTRansactionService.createTransaction(line));
        assertEquals("Line is empty", exception.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "banana,b100", "Apple",
            "b,Apple100", "bapple,100",
            "bApple100", "r,Orange,100,100",
            "r,orange,orange,100", "r,Orange,Orange,,Apple,100",
            "s,s,Orange,,Apple,100", "s,s,Orange,21,Apple,,",
            ",,40","r,,70"
    })
    void createTransaction_incorrectLineFormat_notOk(String line) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                fruitTRansactionService.createTransaction(line));
        assertEquals("Wrong format in line: '" + line
                + "'\nShould be 3 fields separated by a coma. "
                + "Example: 'transaction,fruit,quantity'", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "h,apple,20", "v,apple,20",
            "d,apple,20", "a,orange,441",
            "t,orange,256", "x,orange,12",
            "o,banana,4", "l,banana,4",
            "m,banana,4"
    })
    void createTransaction_incorrectTransactionIndex_notOk(String line) {
        String transactionIndex
                = line.split(LINE_SEPARATOR)[TRANSACTION_FIELD_INDEX];
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fruitTRansactionService.createTransaction(line));
        assertEquals("Incorrect transaction index '"
                        + transactionIndex + "' in line: '" + line + "'",
                exception.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "b,apple,-2", "s,apple,-100",
            "p,apple,-300", "r,apple,-48",
            "b,banana,-10000", "s,banana,-46",
            "p,banana,-34", "r,banana,-4",
            "b,orange,-441", "s,orange,-256",
            "p,orange,-12","r,orange,-98"
    })
    void createTransaction_negativeQuantity_notOk(String line) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                fruitTRansactionService.createTransaction(line));
        assertEquals("Quantity can't be less than zero in line: '"
                + line + "'", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "b,apple,one", "s,apple,number",
            "p,apple,cat", "r,apple,1I1",
            "p,banana,word", "r,banana,one",
            "b,orange,44.1", "s,orange,2.56",
            "p,orange,0.12", "r,orange,six6"
    })
    void createTransaction_quantityAdditionalCharacters_notOk(String line) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                fruitTRansactionService.createTransaction(line));
        assertEquals("Wrong format of quantity field in line: '" + line
                + "'\n The field should be an integer number", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "b ,apple,20", " s,apple,45",
            "p  ,apple,1", "r , apple, 0",
            "b ,  banana,1 000 0", "s,banana, 46",
            "p, banana ,34", "r ,banana , 4",
            "b,orange,4 41 ", "s,orange, 256",
            " p , o r a n g e , 1 2 ","r,ora nge,9 8"
    })
    void createTransaction_spacesInLine_notOk(String line) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                fruitTRansactionService.createTransaction(line));
        assertEquals("Line '" + line
                + "' shouldn't contain"
                + " spaces and upper case letters",
                exception.getMessage());
    }
}
