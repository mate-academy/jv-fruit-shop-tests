package core.basesyntax.service;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParsingServiceImplTest {
    private static final String KEY_APPLE = "apple";
    private static final String KEY_BANANA = "banana";
    private static FruitDao fruitDao;
    private static ParsingService parsingService;
    private static List<FruitTransaction> actualFruitTransactions;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeClass
    public static void beforeClass() {
        parsingService = new TransactionParsingServiceImpl();
        fruitDao = new FruitDaoImpl();
        handlerMap = new HashMap<>();
        handlerMap.put(BALANCE, new AddOperationHandler(fruitDao));
        handlerMap.put(SUPPLY, new AddOperationHandler(fruitDao));
        handlerMap.put(PURCHASE, new AddOperationHandler(fruitDao));
        handlerMap.put(RETURN, new AddOperationHandler(fruitDao));
        actualFruitTransactions = new ArrayList<>(List.of(
                new FruitTransaction(BALANCE, KEY_BANANA,200),
                new FruitTransaction(SUPPLY, KEY_APPLE,100),
                new FruitTransaction(PURCHASE, KEY_BANANA,100),
                new FruitTransaction(RETURN, KEY_APPLE,0)));
    }

    @Test
    public void transactionParsingService_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,200", "s,apple,100",
                "p,banana,100", "r,apple,0");
        assertEquals(parsingService.parse(expected).toString(), actualFruitTransactions.toString());
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
