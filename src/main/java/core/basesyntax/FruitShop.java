package core.basesyntax;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.services.Report;
import core.basesyntax.services.ReportImpl;
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
        FruitShop shop = new FruitShop();

        Map<String, ActionHandler> actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("b",new BalanceHandler());
        actionHandlerMap.put("s",new IncreaseHandler());
        actionHandlerMap.put("p",new PurchaseHandler());
        actionHandlerMap.put("r",new IncreaseHandler());

        Report reportService = new ReportImpl(actionHandlerMap);

        List<String> records = recordDao.readFile(fromFileNamePath);
        String report = reportService.createReport(records);
        recordDao.writeFile(toFileNamePath, report);
    }
}
