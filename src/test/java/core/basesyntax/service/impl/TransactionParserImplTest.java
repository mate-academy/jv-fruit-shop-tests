package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.handler.ReturnHandler;
import core.basesyntax.handler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.strategy.StrategyOptions;
import core.basesyntax.strategy.StrategyOptionsImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static List<String> informationWithFile;
    private static List<String> incorrectInformationWithOptionsInFile;
    private static List<String> incorrectInformationWithFile;
    private static List<String> informationWithIncorrectOptionsStepInFile;
    private static Map<String, Integer> fruitsCounter;
    private static Map<FruitTransaction.Operation, OperationHandler> doingStrategy;
    private static StrategyOptions strategyOptions;
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        doingStrategy = new HashMap<>();
        doingStrategy.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        doingStrategy.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        doingStrategy.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        doingStrategy.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        strategyOptions = new StrategyOptionsImpl(doingStrategy);
        transactionParser = new TransactionParserImpl(strategyOptions);
        fruitsCounter = new HashMap<>();
        informationWithFile = new ArrayList<>();
        informationWithFile.add("type,fruit,quantity");
        informationWithFile.add("b,banana,20");
        informationWithFile.add("s,banana,100");
        informationWithFile.add("p,banana,13");
        informationWithFile.add("b,apple,10");

        incorrectInformationWithOptionsInFile = new ArrayList<>();
        incorrectInformationWithOptionsInFile.add("type,fruit,quantity");
        incorrectInformationWithOptionsInFile.add("k,banana,20");
        incorrectInformationWithOptionsInFile.add("t,apple,100");

        incorrectInformationWithFile = new ArrayList<>();
        incorrectInformationWithFile.add("gfrhjfkdvfjnvjdfkvnjdsf");

        informationWithIncorrectOptionsStepInFile = new ArrayList<>();
        informationWithIncorrectOptionsStepInFile.add("type,fruit,quantity");
        informationWithIncorrectOptionsStepInFile.add("r,banana,20");
        informationWithIncorrectOptionsStepInFile.add("p,apple,100");
    }

    @Before
    public void setUp() {
        fruitsCounter.put("banana", 107);
        fruitsCounter.put("apple", 10);
    }

    @Test
    public void saved_data_isOk() {
        Map<String, Integer> fruits = transactionParser.saveToDb(informationWithFile);
        assertTrue("Expected data: " + fruits.get("banana") + ", but was: "
                + fruitsCounter.get("banana"), fruitsCounter.get("banana") == fruits.get("banana"));
        assertTrue("Expected data: " + fruits.get("apple") + ", but was: "
                + fruitsCounter.get("apple"), fruitsCounter.get("apple") == fruits.get("apple"));
    }

    @Test (expected = RuntimeException.class)
    public void saved_data_isNotOk() {
        transactionParser.saveToDb(informationWithIncorrectOptionsStepInFile);
    }

    @Test(expected = RuntimeException.class)
    public void saved_nullData_isNotOk() {
        transactionParser.saveToDb(null);
    }

    @Test(expected = RuntimeException.class)
    public void saved_incorrectData_isNotOk() {
        transactionParser.saveToDb(incorrectInformationWithFile);
    }

    @Test(expected = RuntimeException.class)
    public void saved_dataWithIncorrectOption_isNotOk() {
        transactionParser.saveToDb(incorrectInformationWithOptionsInFile);
    }
}
