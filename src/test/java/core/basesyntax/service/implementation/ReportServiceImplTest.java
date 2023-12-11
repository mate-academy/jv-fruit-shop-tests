package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.action.Action;
import core.basesyntax.action.ActionHandler;
import core.basesyntax.action.BalanceHandler;
import core.basesyntax.db.DataStorage;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final List<String> listOfData
            = List.of("b,banana,20", "b,apple,100");
    public static final Map<String, Integer> mapOfFruits
            = Map.of("banana", 0, "apple", 0);
    private static final String CORRECT_REPORT = "fruit,quantity\nbanana,20\napple,100\n";
    private static final Map<Action, ActionHandler> actionHandlersMap = new HashMap<>();
    private static ActionHandler balanceHandler;
    private static ReportService reportService;
    private static DataStorage dataStorage;
    private static FruitStorage fruitStorage;

    @BeforeAll
    public static void setUp() {
        reportService = new ReportServiceImpl(actionHandlersMap);
        dataStorage = new DataStorage();
        dataStorage.getListOfData().clear();
        dataStorage.getListOfData().addAll(listOfData);
        fruitStorage = new FruitStorage();
        fruitStorage.getMapOfFruits().clear();
        fruitStorage.getMapOfFruits().putAll(mapOfFruits);
        balanceHandler = new BalanceHandler();
        actionHandlersMap.put(Action.BALANCE, balanceHandler);
    }

    @Test
    public void getReport_CorrectData_Ok() {
        String actual = reportService.getReport();
        assertEquals(CORRECT_REPORT, actual);
    }
}
