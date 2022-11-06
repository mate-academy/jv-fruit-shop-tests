package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final String KEY_APPLE = "apple";
    private static final String KEY_BANANA = "banana";
    private static FruitDao fruitDao;
    private static FruitTransactionParser fruitTransactionParser;
    private static List<FruitTransaction> actualFruitTransactions;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
        fruitDao = new FruitDaoImpl();
        handlerMap = new HashMap<>();
        handlerMap.put(BALANCE, new BalanceOperationHandlerImpl(fruitDao));
        handlerMap.put(SUPPLY, new AddOperationHandlerImpl(fruitDao));
        handlerMap.put(PURCHASE, new SubtractOperationHandlerImpl(fruitDao));
        handlerMap.put(RETURN, new ReturnOperationHandlerImpl(fruitDao));
        actualFruitTransactions = new ArrayList<>(List.of(
                new FruitTransaction(BALANCE, KEY_BANANA,200),
                new FruitTransaction(SUPPLY, KEY_APPLE,100),
                new FruitTransaction(PURCHASE, KEY_BANANA,100),
                new FruitTransaction(RETURN, KEY_APPLE,0)));
    }

    @Test
    public void parseData_IsOk() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,200", "s,apple,100",
                "p,banana,100", "r,apple,0");
        assertEquals(fruitTransactionParser.parseData(expected).toString(),
                actualFruitTransactions.toString());
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
