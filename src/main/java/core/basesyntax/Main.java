package core.basesyntax;

import core.basesyntax.model.ActivityType;
import core.basesyntax.service.ActivityService;
import core.basesyntax.service.ActivityServiceImpl;
import core.basesyntax.service.ActivityTypeStrategy;
import core.basesyntax.service.ActivityTypeStrategyImpl;
import core.basesyntax.service.CsvFileReader;
import core.basesyntax.service.CsvFileReaderImpl;
import core.basesyntax.service.CsvFileWriter;
import core.basesyntax.service.CsvFileWriterImpl;
import core.basesyntax.service.activityhandler.ActivityHandler;
import core.basesyntax.service.activityhandler.BalanceHandler;
import core.basesyntax.service.activityhandler.PurchaseHandler;
import core.basesyntax.service.activityhandler.ReturnHandler;
import core.basesyntax.service.activityhandler.SupplyHandler;
import core.basesyntax.service.validators.InputValidator;
import core.basesyntax.service.validators.InputValidatorImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String FROM_FILE
            = "src/main/java/core/basesyntax/files/activities.csv";
    public static final String TO_FILE
            = "src/main/java/core/basesyntax/files/report.csv";

    public static void main(String[] args) {
        BalanceHandler balanceActivityHandler = new BalanceHandler();
        SupplyHandler supplyActivityHandler = new SupplyHandler();
        PurchaseHandler purchaseActivityHandler = new PurchaseHandler();
        ReturnHandler returnActivityHandler = new ReturnHandler();
        Map<ActivityType, ActivityHandler> activityTypeHandlerMap = new HashMap<>();
        activityTypeHandlerMap.put(ActivityType.b, balanceActivityHandler);
        activityTypeHandlerMap.put(ActivityType.s, supplyActivityHandler);
        activityTypeHandlerMap.put(ActivityType.p, purchaseActivityHandler);
        activityTypeHandlerMap.put(ActivityType.r, returnActivityHandler);
        ActivityTypeStrategy activityTypeStrategy =
                new ActivityTypeStrategyImpl(activityTypeHandlerMap);
        CsvFileReader csvFileReader = new CsvFileReaderImpl();
        List<String> activities = csvFileReader.read(FROM_FILE);
        InputValidator validator = new InputValidatorImpl();
        try {
            validator.validate(activities);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        Map<String, Integer> reportStorage;
        ActivityService activityService = new ActivityServiceImpl();
        reportStorage = activityService.processActivities(activityTypeStrategy, activities);
        CsvFileWriter csvFileWriter = new CsvFileWriterImpl();
        csvFileWriter.write(TO_FILE, reportStorage);
    }
}
