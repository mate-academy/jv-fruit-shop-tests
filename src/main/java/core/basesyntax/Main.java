package core.basesyntax;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.service.CreatReport;
import core.basesyntax.service.ReadFromFile;
import core.basesyntax.service.WriteToDB;
import core.basesyntax.service.WriteToFile;
import core.basesyntax.service.impl.ReadFromFileImpl;
import core.basesyntax.service.impl.ReportCreator;
import core.basesyntax.service.impl.WriteToDbFromList;
import core.basesyntax.service.impl.WriteToFileImpl;
import core.basesyntax.strategy.DoActivities;
import core.basesyntax.strategy.strategy.impl.BalanceReadActivity;
import core.basesyntax.strategy.strategy.impl.PurchaseActivity;
import core.basesyntax.strategy.strategy.impl.ReturnActivity;
import core.basesyntax.strategy.strategy.impl.SupplyActivity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String SPLITTER = ",";
    private static final String DATA_FILE = "src/main/resources/file.txt";
    private static final String REPORT_FILE = "src/main/resources/report.txt";
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";

    public static void main(String[] args) {
        FruitStorageDao storageDao = new FruitStorageDaoImpl();
        Map<String, DoActivities> strategyMap = new HashMap<>();
        strategyMap.put(BALANCE, new BalanceReadActivity(storageDao));
        strategyMap.put(SUPPLY, new SupplyActivity(storageDao));
        strategyMap.put(PURCHASE, new PurchaseActivity(storageDao));
        strategyMap.put(RETURN, new ReturnActivity(storageDao));
        ReadFromFile reader = new ReadFromFileImpl();
        List<String> activities = reader.readFormFile(DATA_FILE);
        WriteToDB activityWriter = new WriteToDbFromList(SPLITTER);
        activityWriter.writeToDB(activities, strategyMap);
        CreatReport reporter = new ReportCreator(storageDao);
        List<String> report = reporter.creatReport();
        WriteToFile writer = new WriteToFileImpl();
        writer.writeToFile(REPORT_FILE, report);
    }
}
