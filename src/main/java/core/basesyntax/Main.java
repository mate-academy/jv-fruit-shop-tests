package core.basesyntax;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.fruit.Fruit;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.BalanceCounter;
import core.basesyntax.service.DataParcer;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.ReportMaker;
import core.basesyntax.service.ReportWriterToFile;
import core.basesyntax.service.actiontype.ActionStrategyBalance;
import core.basesyntax.service.actiontype.ActionStrategyProducer;
import core.basesyntax.service.actiontype.ActionStrategyReturner;
import core.basesyntax.service.actiontype.ActionStrategySupplier;
import core.basesyntax.service.actiontype.ActionType;
import core.basesyntax.service.impl.ActionStrategyImpl;
import core.basesyntax.service.impl.BalanceCounterImpl;
import core.basesyntax.service.impl.DataParcerImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.ReportMakerImpl;
import core.basesyntax.service.impl.ReportWriterToFileImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_ACTION_PER_DAY = "src/main/resources/ActionsPerDay.csv";
    private static final String FILE_REPORT_PER_DAY = "src/main/resources/ReportPerDay.csv";
    private static final Map<String, ActionType> mapStrategy = new HashMap<>();

    public static void main(String[] args) {
        mapStrategy.put("b", new ActionStrategyBalance());
        mapStrategy.put("p", new ActionStrategyProducer());
        mapStrategy.put("s", new ActionStrategySupplier());
        mapStrategy.put("r", new ActionStrategyReturner());
        ActionStrategy actionStrategy = new ActionStrategyImpl(mapStrategy);

        ActionsDao actionsDao = new ActionsDaoImpl(Storage.data);
        FileReader readFile = new FileReaderImpl();
        List<String> inputValues = readFile.getData(FILE_ACTION_PER_DAY);

        DataParcer parceFruitMoving = new DataParcerImpl();
        List<Fruit> fruitsMoving = parceFruitMoving.getFruitsMoving(inputValues);

        BalanceCounter getBalance = new BalanceCounterImpl(actionsDao);
        getBalance.calculateBalance(fruitsMoving, actionStrategy);

        ReportMaker prepareReport = new ReportMakerImpl(actionsDao);
        String stringReport = prepareReport.makeReport();

        ReportWriterToFile writeToFile = new ReportWriterToFileImpl();
        writeToFile.writeReportToFile(stringReport, FILE_REPORT_PER_DAY);
        System.out.println("Report was written to file: " + FILE_REPORT_PER_DAY);
    }
}
