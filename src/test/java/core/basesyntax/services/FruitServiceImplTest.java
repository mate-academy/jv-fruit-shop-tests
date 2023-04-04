package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
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
import org.junit.AfterClass;
import org.junit.Test;

public class FruitServiceImplTest {

    @AfterClass
    public static void clearStorage() {
        Storage.products.clear();
    }

    @Test
    public void run_ok() {
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

        String fromFile = "src/test/java/core/basesyntax/data.csv";
        String toFile = "src/test/java/core/basesyntax/report.csv";
        fruitService.run(fromFile, toFile);

        String expectedReport = "name,quantity\n"
                + "banana,20\n"
                + "apple,100";

        String actualReport = String.join("\n", reader.read(toFile));
        assertEquals(expectedReport, actualReport);
    }
}
