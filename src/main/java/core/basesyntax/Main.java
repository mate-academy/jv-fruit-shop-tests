package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.ActionStrategyImpl;
import core.basesyntax.service.CsvFileReader;
import core.basesyntax.service.CsvFileReaderImpl;
import core.basesyntax.service.CsvReportGenerator;
import core.basesyntax.service.CsvReportGeneratorImpl;
import core.basesyntax.service.CsvReportWriter;
import core.basesyntax.service.CsvReportWriterImpl;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.action.ActionHandler;
import core.basesyntax.service.action.BalanceAction;
import core.basesyntax.service.action.PurchaseAction;
import core.basesyntax.service.action.ReturnAction;
import core.basesyntax.service.action.SupplyAction;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_PATH_FOR_DATABASE =
            "src/main/resources/data.csv";
    private static final String FILE_PATH_FOR_FINALREPORT =
            "src/main/resources/report.csv";
    private static final Map<Operation, ActionHandler> actionHandlerMap = Map.of(
            Operation.BALANCE, new BalanceAction(),
            Operation.PURCHASE, new PurchaseAction(),
            Operation.RETURN, new ReturnAction(),
            Operation.SUPPLY, new SupplyAction()
    );

    public static void main(String[] arg) {

        CsvFileReader fileReader = new CsvFileReaderImpl();
        List<String> textFromDatabase = fileReader.read(FILE_PATH_FOR_DATABASE);

        FruitTransactionParser fruitTransactionParser = new FruitTransactionParser();
        List<FruitTransaction> allTransactions = fruitTransactionParser
                .parseTransaction(textFromDatabase);

        ActionStrategy actionStrategy = new ActionStrategyImpl(actionHandlerMap);
        ShopService shopService = new ShopServiceImpl(actionStrategy);
        shopService.process(allTransactions);

        CsvReportGenerator reportWriter = new CsvReportGeneratorImpl();
        String reportInfo = reportWriter.generateReport();

        CsvReportWriter csvReportWriter = new CsvReportWriterImpl();
        csvReportWriter.write(reportInfo, FILE_PATH_FOR_FINALREPORT);
    }
}
