package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.action.Action;
import core.basesyntax.action.ActionHandler;
import core.basesyntax.action.BalanceHandler;
import core.basesyntax.action.PurchaseHandler;
import core.basesyntax.action.ReturnHandler;
import core.basesyntax.action.SupplyHandler;
import core.basesyntax.db.FruitStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ActionStrategyImplTest {
    private static final String COMMA = ",";
    private static final int ACTION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final List<String> listOfData = new ArrayList<>();
    private static final Map<String, Integer> mapOfFruits = new HashMap<>();
    private static final ActionHandler balanceHandler = new BalanceHandler();
    private static final ActionHandler purchaseHandler = new PurchaseHandler();
    private static final ActionHandler returnHandler = new ReturnHandler();
    private static final ActionHandler supplyHandler = new SupplyHandler();
    private static final Map<Action, ActionHandler> actionHandlersMap = new HashMap<>();
    private static ActionStrategy actionStrategy;
    private static FruitStorage fruitStorage;

    @BeforeAll
    public static void setUp() {
        listOfData.addAll(List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"));
        actionHandlersMap.putAll(Map.of(
                Action.BALANCE, balanceHandler,
                Action.RETURN, returnHandler,
                Action.PURCHASE, purchaseHandler,
                Action.SUPPLY, supplyHandler));
        mapOfFruits.putAll(Map.of(
                "banana", 0,
                "apple", 0));
        actionStrategy = new ActionStrategyImpl(actionHandlersMap);
        fruitStorage = new FruitStorage();
        fruitStorage.getMapOfFruits().clear();
        fruitStorage.getMapOfFruits().putAll(mapOfFruits);
    }

    @Test
    public void get_CorrectData_Ok() {
        Map<String, Integer> expected = Map
                .of("banana", 152,
                        "apple", 90);
        for (String string : listOfData) {
            int newAmount = actionStrategy
                    .get(getAction(string))
                    .performAction(getFruitName(string),
                            getAmount(string));
            fruitStorage.getMapOfFruits()
                    .put(getFruitName(string), newAmount);
        }
        assertEquals(expected, fruitStorage.getMapOfFruits());
    }

    private static String getFruitName(String string) {
        return string.split(COMMA)[FRUIT_INDEX];
    }

    private static int getAmount(String string) {
        return Integer.parseInt(string
                .split(COMMA)[AMOUNT_INDEX]);
    }

    private static Action getAction(String string) {
        return Action.getAction(string
                .split(COMMA)[ACTION_INDEX]);
    }
}
