import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.services.FileReaderService;
import core.basesyntax.services.FileReaderServiceImpl;
import core.basesyntax.services.FileWriterService;
import core.basesyntax.services.FileWriterServiceImpl;
import core.basesyntax.services.FruitService;
import core.basesyntax.services.FruitServiceImpl;
import core.basesyntax.services.OperationStrategy;
import core.basesyntax.services.OperationStrategyImpl;
import core.basesyntax.services.ReportService;
import core.basesyntax.services.ReportServiceImpl;
import core.basesyntax.services.operation.BalanceOperation;
import core.basesyntax.services.operation.OperationHandler;
import core.basesyntax.services.operation.PurchaseOperation;
import core.basesyntax.services.operation.ReturnOperation;
import core.basesyntax.services.operation.SupplyOperation;
import core.basesyntax.services.transaction.ProductTransactionMapper;
import core.basesyntax.services.transaction.ProductTransactionMapperImpl;
import core.basesyntax.services.transaction.TransactionService;
import core.basesyntax.services.transaction.TransactionServiceImpl;
import core.basesyntax.services.transaction.model.ProductTransaction;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ProductDao dao = new ProductDaoImpl();
        Map<ProductTransaction.Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(ProductTransaction.Operation.BALANCE, new BalanceOperation(dao));
        operationMap.put(ProductTransaction.Operation.PURCHASE, new PurchaseOperation(dao));
        operationMap.put(ProductTransaction.Operation.SUPPLY, new SupplyOperation(dao));
        operationMap.put(ProductTransaction.Operation.RETURN, new ReturnOperation(dao));
        OperationStrategy strategy = new OperationStrategyImpl(operationMap);
        FileReaderService reader = new FileReaderServiceImpl();
        ProductTransactionMapper mapper = new ProductTransactionMapperImpl();
        FileWriterService writer = new FileWriterServiceImpl();
        ReportService report = new ReportServiceImpl();
        TransactionService trService = new TransactionServiceImpl(strategy);
        FruitService fruitService =
                new FruitServiceImpl(reader, writer, mapper, dao, report, trService);
        String fromFile = "src/main/java/data.csv";
        String toFile = "report.csv";
        fruitService.run(fromFile, toFile);
    }
}
