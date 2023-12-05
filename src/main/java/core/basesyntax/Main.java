package core.basesyntax;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoCsvImpl;
import core.basesyntax.dao.ReportDao;
import core.basesyntax.dao.ReportDaoImpl;
import core.basesyntax.model.Product;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportSender;
import core.basesyntax.service.TransactionProcessing;
import core.basesyntax.service.impl.ReportGeneratorCsvImpl;
import core.basesyntax.service.impl.ReportSenderCsvImpl;
import core.basesyntax.service.impl.TransactionProcessingImpl;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.service.transaction.impl.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.impl.ReturnTransactionHandler;
import core.basesyntax.service.transaction.impl.SupplyTransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.impl.TransactionStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String TRANSACTIONS_CSV_FILE_NAME =
            "src/main/java/core/basesyntax/resources/transactions.csv";
    public static final String REPORT_CSV_FILE_PATH =
            "src/main/java/core/basesyntax/resources/report.csv";

    public static void main(String[] args) {
        Map<Transaction, TransactionHandler> transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(Transaction.SUPPLY, new SupplyTransactionHandler());
        transactionHandlerMap.put(Transaction.PURCHASE, new PurchaseTransactionHandler());
        transactionHandlerMap.put(Transaction.RETURN,
            new ReturnTransactionHandler());

        TransactionStrategy transactionStrategy =
                new TransactionStrategyImpl(transactionHandlerMap);
        ReportDao reportDao = new ReportDaoImpl();
        ProductDao productDao = new ProductDaoCsvImpl(TRANSACTIONS_CSV_FILE_NAME);

        TransactionProcessing transactionProcessing =
                new TransactionProcessingImpl(transactionStrategy);
        ReportGenerator reportGenerator = new ReportGeneratorCsvImpl();
        ReportSender reportSender = new ReportSenderCsvImpl(REPORT_CSV_FILE_PATH);

        List<Product> products = productDao.get();

        List<Product> productsForBalanceSetting =
                transactionProcessing.filterByTransaction(products, Transaction.BALANCE);

        productsForBalanceSetting.forEach(reportDao::update);

        List<Product> productsForTransactions =
                transactionProcessing.filterExceptTransaction(products, Transaction.BALANCE);

        productsForTransactions.forEach(product -> {
            int balanceBeforeTransaction = reportDao.getBalanceForProduct(product);
            Product productAfterTransaction =
                    transactionProcessing.perform(product, balanceBeforeTransaction);
            reportDao.update(productAfterTransaction);
        });

        String report = reportGenerator.create(reportDao.get());
        reportSender.send(report);
    }
}
