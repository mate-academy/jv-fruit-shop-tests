package core.basesyntax;

import core.basesyntax.dao.ReportDao;
import core.basesyntax.dao.impl.ReportDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.BalanceSetter;
import core.basesyntax.service.ConverterFruitTransaction;
import core.basesyntax.service.Reader;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportSender;
import core.basesyntax.service.TransactionProcess;
import core.basesyntax.service.impl.BalanceSetterImpl;
import core.basesyntax.service.impl.ConverterFruitTransactionImpl;
import core.basesyntax.service.impl.ReaderImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ReportSenderImpl;
import core.basesyntax.service.impl.TransactionProcessImpl;
import core.basesyntax.strategy.StrategyFruitTransaction;
import core.basesyntax.strategy.impl.StrategyFruitTransactionImpl;
import core.basesyntax.transaction.OperationHandler;
import core.basesyntax.transaction.impl.PurchaseHandler;
import core.basesyntax.transaction.impl.ReturnHandler;
import core.basesyntax.transaction.impl.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FruitShop {
    private static final String TRANSACTIONS_FILE_PATH
            = "src/main/resources/transactions.csv";
    private static final String REPORT_FILE_PATH
            = "src/main/resources/report.csv";
    private static final Map<Operation, OperationHandler> operations =
            Map.of(Operation.SUPPLY, new SupplyHandler(),
                    Operation.PURCHASE, new PurchaseHandler(),
                    Operation.RETURN, new ReturnHandler());
    private static final Logger logger = LogManager.getLogger(FruitShop.class);

    public static void main(String[] args) {
        StrategyFruitTransaction strategyFruitTransaction =
                new StrategyFruitTransactionImpl(operations);
        ReportDao reportDao = new ReportDaoImpl();
        Reader reader = new ReaderImpl();
        BalanceSetter balanceSetter = new BalanceSetterImpl(reportDao);
        TransactionProcess transactionProcess =
                new TransactionProcessImpl(strategyFruitTransaction, reportDao);
        ReportGenerator reportGenerator = new ReportGeneratorImpl(reportDao);
        ReportSender reportSender = new ReportSenderImpl();
        List<String> readTransactions = reader.read(TRANSACTIONS_FILE_PATH);
        ConverterFruitTransaction converterFruitTransaction = new ConverterFruitTransactionImpl();
        List<FruitTransaction> fruitTransactions =
                converterFruitTransaction.convertToFruitTransaction(readTransactions);
        balanceSetter.setBalance(fruitTransactions);
        fruitTransactions.forEach(transactionProcess::process);
        String report = reportGenerator.generateReport();
        reportSender.send(REPORT_FILE_PATH, report);

        logger.info("Fruit shop finished.");
    }
}
