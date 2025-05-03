package core.basesyntax;

import core.basesyntax.dao.ArticleDao;
import core.basesyntax.dao.ArticleDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Reader;
import core.basesyntax.service.StoreService;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.FileReaderCsvImpl;
import core.basesyntax.service.impl.FileWriterCsvImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.StoreServiceImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import core.basesyntax.strategy.handler.BalanceTransactionHandler;
import core.basesyntax.strategy.handler.PurchaseTransactionHandler;
import core.basesyntax.strategy.handler.ReturnTransactionHandler;
import core.basesyntax.strategy.handler.SupplyTransactionHandler;
import core.basesyntax.strategy.handler.TransactionHandler;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String [] args) {
        final String sourceFile = "src/main/resources/store-info.csv";
        final String reportFile = "src/main/resources/reportCsv.csv";
        final String[] fruits = {"banana", "apple"};

        Map<FruitTransaction.Operation, TransactionHandler> strategyMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceTransactionHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseTransactionHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyTransactionHandler(),
                FruitTransaction.Operation.RETURN, new ReturnTransactionHandler()
        );
        ArticleDao articleDao = new ArticleDaoImpl();
        for (String fruit : fruits) {
            articleDao.addArticle(fruit);
        }
        TransactionStrategy strategy = new TransactionStrategyImpl(strategyMap);

        Reader fileReader = new FileReaderCsvImpl();
        List<String> fruitTransactionsFromCsv = fileReader.read(sourceFile);

        TransactionService transactionService = new FruitTransactionServiceImpl(articleDao);
        TransactionParser transactionParser = new TransactionParserImpl(transactionService);
        List<FruitTransaction> fruitTransactions
                = transactionParser.parse(fruitTransactionsFromCsv);

        StoreService storeService = new StoreServiceImpl(articleDao, strategy);
        storeService.updateStorage(fruitTransactions);
        List<String> fruitsReport = storeService.createReport();

        Writer fileWriterCsv = new FileWriterCsvImpl();
        fileWriterCsv.write(reportFile, fruitsReport);
    }
}
