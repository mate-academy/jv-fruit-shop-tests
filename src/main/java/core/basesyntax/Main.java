package core.basesyntax;

import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.enums.Operation;
import core.basesyntax.services.impl.CsvFileReaderImpl;
import core.basesyntax.services.impl.CsvFileWriterImpl;
import core.basesyntax.services.impl.TransactionProcessorImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.impl.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.impl.OperationHandlerPurchaseImpl;
import core.basesyntax.strategy.impl.OperationHandlerReturnImpl;
import core.basesyntax.strategy.impl.OperationHandlerSupplyImpl;
import core.basesyntax.strategy.impl.StrategyImpl;

public class Main {
    public static void main(String[] args) {
        Strategy strategy = new StrategyImpl();
        strategy.addStrategyType(Operation.BALANCE.getOperation(),
                new OperationHandlerBalanceImpl());
        strategy.addStrategyType(Operation.RETURN.getOperation(),
                new OperationHandlerReturnImpl());
        strategy.addStrategyType(Operation.SUPPLY.getOperation(),
                new OperationHandlerSupplyImpl());
        strategy.addStrategyType(Operation.PURCHASE.getOperation(),
                new OperationHandlerPurchaseImpl());

        CsvFileReaderImpl fileReader = new CsvFileReaderImpl();
        TransactionProcessorImpl transactionProcessor = new TransactionProcessorImpl();
        transactionProcessor.processingData(fileReader
                .getAcceptedFileAsList("src/source/input.csv"), strategy);

        CsvFileWriterImpl fileWriter = new CsvFileWriterImpl();
        fileWriter.createReportFile("src/report.csv", new StorageImpl().getStorageAsList());
    }
}
