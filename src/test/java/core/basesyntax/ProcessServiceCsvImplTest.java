package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.constants.Activity;
import core.basesyntax.db.Database;
import core.basesyntax.db.DatabaseDaoService;
import core.basesyntax.db.impl.DatabaseDaoServiceImpl;
import core.basesyntax.service.impl.ProcessServiceCsvImpl;
import core.basesyntax.strategy.ActivitiesStrategy;
import core.basesyntax.strategy.ActivitiesStrategyImpl;
import core.basesyntax.strategy.handlers.ActivityHandler;
import core.basesyntax.strategy.handlers.impl.BalanceActivityHandler;
import core.basesyntax.strategy.handlers.impl.PurchaseActivityHandler;
import core.basesyntax.strategy.handlers.impl.ReturnActivityHandler;
import core.basesyntax.strategy.handlers.impl.SupplyActivityHandler;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProcessServiceCsvImplTest {
    private static final DatabaseDaoService databaseDaoService = new DatabaseDaoServiceImpl();
    private static final Map<String, ActivityHandler> ACTIVITIES_STRATEGY_MAP = Map.of(
            Activity.BALANCE.getActivity(), new BalanceActivityHandler(databaseDaoService),
            Activity.PURCHASE.getActivity(), new PurchaseActivityHandler(databaseDaoService),
            Activity.RETURN.getActivity(), new ReturnActivityHandler(databaseDaoService),
            Activity.SUPPLY.getActivity(), new SupplyActivityHandler(databaseDaoService)
    );
    private static final ActivitiesStrategy activitiesStrategy =
            new ActivitiesStrategyImpl(ACTIVITIES_STRATEGY_MAP);
    private static final ProcessServiceCsvImpl.DataLineProcessService dataLineProcessService =
            new ProcessServiceCsvImpl.DataLineProcessService();
    private static final List<String> CORRECT_DATA = List.of("b,apple,100",
            "p,apple,50",
            "r,apple,20",
            "s,apple,20");
    private static final List<String> NOT_ENOUGH_PRODUCT_DATA = List.of(
            "b,apple,100",
            "p,apple,200");
    private static final List<String> INCORRECT_DATA = List.of("qwerty");
    private static final String DATA_PRODUCT_NAME = "apple";
    private static final Integer DATA_PRODUCT_AMOUNT = 90;
    private ProcessServiceCsvImpl processService;

    @BeforeEach
    public void setUp() {
        processService = new ProcessServiceCsvImpl(activitiesStrategy, dataLineProcessService);
    }

    @Test
    public void processInfo_nullData_ok() {
        LinkedList<String> data = new LinkedList<>();
        processService.processInfo(data);
        assertTrue(Database.database.isEmpty());
    }

    @Test
    public void processInfo_noCommas_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> processService.processInfo(INCORRECT_DATA));
    }

    @Test
    public void processInfo_correctData_ok() {
        processService.processInfo(CORRECT_DATA);
        assertEquals(DATA_PRODUCT_AMOUNT, Database.database.get(DATA_PRODUCT_NAME));
    }

    @Test
    public void processInfo_notEnoughProduct_notOk() {
        assertThrows(RuntimeException.class,
                () -> processService.processInfo(NOT_ENOUGH_PRODUCT_DATA));
    }

    @AfterEach
    public void afterEachTest() {
        Database.database.clear();
    }
}
