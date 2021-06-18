package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.services.actions.ActionHandler;
import core.basesyntax.services.actions.BalanceHandler;
import core.basesyntax.services.actions.IncreaseHandler;
import core.basesyntax.services.actions.PurchaseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportImplTest {
    private static Report report;

    @Before
    public void before() {
        Storage.fruits.clear();
        Map<String, ActionHandler> actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("b",new BalanceHandler());
        actionHandlerMap.put("s",new IncreaseHandler());
        actionHandlerMap.put("p",new PurchaseHandler());
        actionHandlerMap.put("r",new IncreaseHandler());
        report = new ReportImpl(actionHandlerMap);
    }

    @Test(expected = NullPointerException.class)
    public void buildReportWithWrongData_Not_Ok() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("Ahahahah");
        wrongDataList.add("wrongData,strawberry,20");
        report.createReport(wrongDataList);
    }

    @Test
    public void buildReportWithCorrectData_Ok() {
        List<String> correctDataList = new ArrayList<>();
        correctDataList.add("Heading");
        correctDataList.add("b,banana,10");
        correctDataList.add("b,someUnknownFruit,1");
        String expectedReport = "fruit,quantity\nbanana,10"
                + "\nsomeUnknownFruit,1\n";
        String actualReport = report.createReport(correctDataList);
        Assert.assertEquals(expectedReport,actualReport);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}
