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
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> doingStrategy = new HashMap<>();
        doingStrategy.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        doingStrategy.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        doingStrategy.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        doingStrategy.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        StrategyOptions strategyOptions = new StrategyOptionsImpl(doingStrategy);

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
    public void parseFrom_correctData_isOk() {
        Map<String, Integer> fruits = transactionParser.saveToDb(informationWithFile);
        Integer actual = fruitsCounter.get("banana");
        Integer expected = fruits.get("banana");
        assertTrue("Expected data: " + expected + ", but was: "
                + actual, actual.equals(expected));
        actual = fruitsCounter.get("apple");
        expected = fruits.get("apple");
        assertTrue("Expected data: " + expected + ", but was: "
                + actual, actual.equals(expected));
    }

    @Test (expected = RuntimeException.class)
    public void parseFrom_dataWithIncorrectOptionsStep_NotOk() {
        transactionParser.saveToDb(informationWithIncorrectOptionsStepInFile);
    }

    @Test(expected = RuntimeException.class)
    public void parseFrom_nullData_NotOk() {
        transactionParser.saveToDb(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseFrom_incorrectData_NotOk() {
        transactionParser.saveToDb(incorrectInformationWithFile);
    }

    @Test(expected = RuntimeException.class)
    public void parseFrom_dataWithIncorrectOption_NotOk() {
        transactionParser.saveToDb(incorrectInformationWithOptionsInFile);
    }
}
