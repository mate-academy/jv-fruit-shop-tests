package core.basesyntax;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.services.ReportService;
import core.basesyntax.services.ReportServiceImpl;
import core.basesyntax.services.actions.ActionHandler;
import core.basesyntax.services.actions.BalanceHandler;
import core.basesyntax.services.actions.IncreaseHandler;
import core.basesyntax.services.actions.PurchaseHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShop {
    public static void main(String[] args) {
        final String fromFileNamePath = "src/main/resources/input.txt";
        final String toFileNamePath = "src/main/resources/report.txt";
        final RecordDao recordDao = new RecordDaoImpl();

        Map<String, ActionHandler> actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("b",new BalanceHandler());
        actionHandlerMap.put("s",new IncreaseHandler());
        actionHandlerMap.put("p",new PurchaseHandler());
        actionHandlerMap.put("r",new IncreaseHandler());

        ReportService reportService = new ReportServiceImpl(actionHandlerMap);

        List<String> records = recordDao.readFile(fromFileNamePath);
        String report = reportService.createReport(records);
        recordDao.writeFile(toFileNamePath, report);
    }
}
