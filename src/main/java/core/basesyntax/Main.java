package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionMapper;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.Reader;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FruitTransactionMapperImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.WriterImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Map;

public class Main {
    private static final String FRUIT_SHOP_FILE_NAME = "src\\main\\resources\\During the day.csv";
    private static final String REPORT_FILE_NAME = "src\\main\\resources\\Report file.csv";

    public static void main(String[] args) {
        FruitDao dao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        Reader reader = new FileReaderImpl();
        String fileData = reader.readFile(FRUIT_SHOP_FILE_NAME);

        FruitTransactionMapper converter = new FruitTransactionMapperImpl();
        FruitTransaction[] fruitTransactions = converter.buildFruitTransactions(fileData);

        FruitTransactionService fruitTransactionService =
                new FruitTransactionServiceImpl(dao, operationStrategy);
        fruitTransactionService.processData(fruitTransactions);

        ReportCreator creator = new ReportCreatorImpl();
        String report = creator.createReport();

        Writer writer = new WriterImpl();
        writer.write(report, REPORT_FILE_NAME);
    }
}
