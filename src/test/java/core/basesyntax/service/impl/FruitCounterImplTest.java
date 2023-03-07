package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.actions.BalanceAction;
import core.basesyntax.actions.DoingAction;
import core.basesyntax.actions.PurchaseAction;
import core.basesyntax.actions.ReturnAction;
import core.basesyntax.actions.SupplyAction;
import core.basesyntax.model.FruitDto;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitCounter;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.SaveService;
import core.basesyntax.strategy.StrategyOptions;
import core.basesyntax.strategy.StrategyOptionsImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitCounterImplTest {
    private static final String WORKER_FILE = "src/main/resources/testInput.csv";
    private static List<FruitDto> expectedFruits;
    private static FruitCounter fruitCounter;
    private static Map<FruitTransaction.Operation, DoingAction> doingStrategy;
    private static StrategyOptions strategyOptions;
    private static ReaderService reader;
    private static SaveService saved;
    
    @BeforeClass
    public static void beforeClass() {
        expectedFruits = new ArrayList<>();
        doingStrategy = new HashMap<>();
        doingStrategy.put(FruitTransaction.Operation.BALANCE, new BalanceAction());
        doingStrategy.put(FruitTransaction.Operation.SUPPLY, new SupplyAction());
        doingStrategy.put(FruitTransaction.Operation.PURCHASE, new PurchaseAction());
        doingStrategy.put(FruitTransaction.Operation.RETURN, new ReturnAction());
        strategyOptions = new StrategyOptionsImpl(doingStrategy);
        fruitCounter = new FruitCounterImpl(strategyOptions);
        reader = new ReaderServiceImpl();
        saved = new SaveServiceImpl();
    }

    @Before
    public void setUp() {
        FruitDto fruit = new FruitDto("banana",152);
        expectedFruits.add(fruit);
        FruitDto fruit1 = new FruitDto("apple",90);
        expectedFruits.add(fruit1);
    }

    @Test
    public void calculateFruit_withCorrectData_isOk() {
        List<String> strings = reader.readTransactionWithFile(WORKER_FILE);
        saved.saveToDb(strings);
        List<FruitDto> fruitDtos = fruitCounter.countReport();
        for (int i = 0; i < fruitDtos.size(); i++) {
            assertEquals(fruitDtos.get(i).getFruit(), expectedFruits.get(i).getFruit());
            assertEquals(fruitDtos.get(i).getQuantity(), expectedFruits.get(i).getQuantity());
        }
    }
}
