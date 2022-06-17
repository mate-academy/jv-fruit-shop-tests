package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.strategy.TransactionsStrategy;
import core.basesyntax.strategy.impl.TransactionsStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static DataParserService parserService;
    private static TransactionsStrategy transactionsStrategy;
    private static Map<String, FruitTransaction.Operation> operationMap;
    private List<String> dataFromFile;
    private List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationMap = new HashMap<>();
        operationMap.put("b", FruitTransaction.Operation.BALANCE);
        operationMap.put("p", FruitTransaction.Operation.PURCHASE);
        operationMap.put("r", FruitTransaction.Operation.RETURN);
        operationMap.put("s", FruitTransaction.Operation.SUPPLY);
        transactionsStrategy = new TransactionsStrategyImpl(operationMap);
        parserService = new DataParserServiceImpl(transactionsStrategy);
    }

    @Before
    public void setUp() throws Exception {
        dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,200");
        dataFromFile.add("s,banana,50");
        dataFromFile.add("r,banana,100");
        dataFromFile.add("p,banana,10");
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(FruitTransaction.of(FruitTransaction.Operation.BALANCE,
                "banana", 200));
        fruitTransactions.add(FruitTransaction.of(FruitTransaction.Operation.SUPPLY,
                "banana", 50));
        fruitTransactions.add(FruitTransaction.of(FruitTransaction.Operation.RETURN,
                "banana", 100));
        fruitTransactions.add(FruitTransaction.of(FruitTransaction.Operation.PURCHASE,
                "banana", 10));

    }

    @Test(expected = RuntimeException.class)
    public void parse_inputListIsNull_notOk() {
        parserService.parse(null);
    }

    @Test
    public void parse_inputIsNormalValue_ok() {
        List<FruitTransaction> actual = parserService.parse(dataFromFile);
        List<FruitTransaction> expected = fruitTransactions;
        Assert.assertEquals(expected, actual);
    }
}
