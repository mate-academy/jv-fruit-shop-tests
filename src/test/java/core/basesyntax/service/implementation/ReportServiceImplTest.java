package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.action.Action;
import core.basesyntax.action.ActionHandler;
import core.basesyntax.action.BalanceHandler;
import core.basesyntax.action.PurchaseHandler;
import core.basesyntax.action.ReturnHandler;
import core.basesyntax.action.SupplyHandler;
import core.basesyntax.dao.DataDao;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.implementation.DataDaoImpl;
import core.basesyntax.dao.implementation.FruitDaoImpl;
import core.basesyntax.service.DataService;
import core.basesyntax.service.FileService;
import core.basesyntax.service.ReportService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final String FROM_FILE_PATH = "src/main/resources/test.csv";
    private static final String CORRECT_REPORT = "fruit,quantity\nbanana,152\napple,90\n";
    private static final ActionHandler balanceHandler = new BalanceHandler();
    private static final ActionHandler purchaseHandler = new PurchaseHandler();
    private static final ActionHandler returnHandler = new ReturnHandler();
    private static final ActionHandler supplyHandler = new SupplyHandler();
    private static final Map<Action, ActionHandler> actionHandlersMap = Map.of(
            Action.BALANCE, balanceHandler,
            Action.RETURN, returnHandler,
            Action.PURCHASE, purchaseHandler,
            Action.SUPPLY, supplyHandler);
    private static FileService fileService;
    private static DataService dataService;
    private static ReportService reportService;
    private static DataDao dataDao;
    private static FruitDao fruitDao;

    @BeforeAll
    public static void setUp() {
        fileService = new FileServiceImpl();
        dataService = new DataServiceImpl();
        reportService = new ReportServiceImpl(actionHandlersMap);
        dataDao = new DataDaoImpl();
        fruitDao = new FruitDaoImpl();
        dataDao.getData().clear();
        fruitDao.getMap().clear();
    }

    @Test
    public void getReport_CorrectData_Ok() {
        List<String> dataFromFile = fileService.readFromFile(FROM_FILE_PATH);
        dataService.fillDataStorage(dataFromFile);
        dataService.fillFruitStorage();
        String actual = reportService.getReport();
        assertEquals(CORRECT_REPORT, actual);
    }
}
