package core.basesyntax.basesyntax;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.fruittransactionparser.FruitTransactionParser;
import core.basesyntax.service.fruittransactionparser.FruitTransactionParserImpl;
import core.basesyntax.service.report.ReportService;
import core.basesyntax.service.report.ReportServiceImpl;
import core.basesyntax.service.transactionexecutor.TransactionExecutor;
import core.basesyntax.service.transactionexecutor.TransactionExecutorImpl;
import core.basesyntax.service.writereadcsv.FileReader;
import core.basesyntax.service.writereadcsv.FileReaderImp;
import core.basesyntax.service.writereadcsv.FileWriter;
import core.basesyntax.service.writereadcsv.FileWriterImpl;
import core.basesyntax.strategy.operationmap.OperationMapImpl;
import core.basesyntax.strategy.operationstrategy.OperationStrategy;
import core.basesyntax.strategy.operationstrategy.OperationStrategyImpl;
import core.basesyntax.strategy.transactionhandlers.TransactionHandler;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_TO_WRITE =
            "src/main/java/core/basesyntax/resources/outputdate/report.csv";
    private static final String FILE_TO_READ =
            "src/main/java/core/basesyntax/resources/input/fruit.csv";

    public static void main(String[] args) {
        FileReader readCsv = new FileReaderImp();
        List<String> readFromFileCsv = readCsv.readFromFileCsv(FILE_TO_READ);
        FruitTransactionParser parse = new FruitTransactionParserImpl();
        List<FruitTransaction> list = parse.parseToFruitTransactions(readFromFileCsv);
        Map<FruitTransaction.Operation, TransactionHandler> map = new OperationMapImpl()
                .getOperationMap();
        OperationStrategy strategy = new OperationStrategyImpl(map);
        TransactionExecutor executor = new TransactionExecutorImpl(strategy);
        executor.executeTransaction(list);
        FruitStorageDao dao = new FruitStorageDaoImpl();
        ReportService reportService = new ReportServiceImpl(dao);
        String reportToWrite = reportService.getReport();
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFileCsv(reportToWrite,FILE_TO_WRITE);
    }
}
