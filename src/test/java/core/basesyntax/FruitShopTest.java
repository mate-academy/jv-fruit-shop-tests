package core.basesyntax;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.services.Report;
import core.basesyntax.services.ReportImpl;
import core.basesyntax.services.actions.ActionHandler;
import core.basesyntax.services.actions.BalanceHandler;
import core.basesyntax.services.actions.IncreaseHandler;
import core.basesyntax.services.actions.PurchaseHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitShopTest {
    private RecordDao recordDao;
    private Report report;

    @Before
    public void before() {
        Map<String, ActionHandler> actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("b",new BalanceHandler());
        actionHandlerMap.put("s",new IncreaseHandler());
        actionHandlerMap.put("p",new PurchaseHandler());
        actionHandlerMap.put("r",new IncreaseHandler());
        report = new ReportImpl(actionHandlerMap);
        recordDao = new RecordDaoImpl();
    }

    @Test
    public void storeSendReport_Ok() {
        String fileName = "src/test/resources/output.txt";
        List<String> correctDataList = createListWithCorrectData();
        String actualReport = report.createReport(correctDataList);
        recordDao.writeFile(fileName, actualReport);
        List<String> excepted = Arrays.asList("fruit,quantity", "banana,10", "someUnknownFruit,1");
        List<String> actual = recordDao.readFile(fileName);
        Assert.assertEquals(excepted,actual);
    }

    List<String> createListWithCorrectData() {
        List<String> correctDataList = new ArrayList<>();
        correctDataList.add("Heading");
        correctDataList.add("b,banana,10");
        correctDataList.add("b,someUnknownFruit,1");
        return correctDataList;
    }
}
