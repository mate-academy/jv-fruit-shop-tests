package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.report.FruitBalance;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.ActivityStrategyImpl;
import core.basesyntax.strategy.activity.ActivityHandler;
import core.basesyntax.strategy.activity.ActivityHandlerAddImpl;
import core.basesyntax.strategy.activity.ActivityHandlerSubstractionImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static Map<String, ActivityHandler> activityHandlerMap;
    private static ReportCreator reportCreator;
    private static RecordTransformer recordTransformer;
    private final List<String> inputList =
            List.of("b,banana,20", "b,apple,100","s,banana,100","p,apple,10");
    private final List<String> testListTrue = List.of("banana,120", "apple,90");
    private final List<String> testListFalse = List.of("banana,115", "apple,85");

    @BeforeAll
    static void beforeAll() {
        activityHandlerMap = new HashMap<>();
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        reportCreator = new ReportCreatorImpl(activityStrategy);
        recordTransformer = new RecordTransformerImpl();
    }

    @BeforeEach
    void setUp() {
        recordTransformer.transform(inputList);
        activityHandlerMap.put(BALANCE, new ActivityHandlerAddImpl());
        activityHandlerMap.put(SUPPLY, new ActivityHandlerAddImpl());
        activityHandlerMap.put(PURCHASE, new ActivityHandlerSubstractionImpl());
        activityHandlerMap.put(RETURN, new ActivityHandlerAddImpl());
    }

    @Test
    void createReportTrue() {
        assertEquals(reportCreator.createReport(), testListTrue);
    }

    @Test
    void createReportFalse() {
        assertNotEquals(reportCreator.createReport(), testListFalse);
    }

    @Test
    void createReportBanana() {
        assertEquals(reportCreator.createReport().get(0), testListTrue.get(0));
    }

    @Test
    void createReportApple() {
        assertEquals(reportCreator.createReport().get(1), testListTrue.get(1));
    }

    @AfterEach
    void tearDown() {
        FruitBalance.FRUIT_BALANCE.clear();
        Storage.records.clear();
    }
}
