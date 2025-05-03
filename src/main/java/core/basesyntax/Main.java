package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.impl.TransactionStrategyImpl;
import core.basesyntax.utils.ListUtil;
import core.basesyntax.utils.ReportUtil;
import core.basesyntax.utils.StrategyUtil;
import java.util.List;

public class Main {
    private static final String FRUITS_CSV_FILEPATH = "src/main/resources/fruits.csv";
    private static final String REPORT_CSV_FILEPATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        final StorageDao storageDao = new FruitStorageDao(new FruitStorage());
        final List<String> fileContent = new ReaderServiceImpl().readFromFile(FRUITS_CSV_FILEPATH);
        final TransactionStrategy strategy
                = new TransactionStrategyImpl(new StrategyUtil().initStrategyMap(storageDao));

        new ListUtil().processList(fileContent, strategy);
        List<String> reportList = new ReportUtil().generateReport(storageDao);
        new WriterServiceImpl().writeToFile(reportList, REPORT_CSV_FILEPATH);
    }
}
