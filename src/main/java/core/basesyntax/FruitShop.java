package core.basesyntax;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.file_reader.ReaderFileImpl;
import core.basesyntax.service.file_writer.WriteToFile;
import core.basesyntax.service.file_writer.WriteToFileImpl;

import core.basesyntax.service.operation.BalanceOperationHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandlerImpl;
import core.basesyntax.service.operation.SupplyOperationHandlerImpl;
import core.basesyntax.service.operation.PurchaseOperationHandlerImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.transaction.FruitTransactionToFile;
import core.basesyntax.service.transaction.FruitTransactionToFileImpl;
import core.basesyntax.service.transaction.ReportListFruit;
import core.basesyntax.service.transaction.ReportListFruitImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShop {
    private static final String INPUT_FILE = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/resources/output.csv";
    private static final FruitShopDao FRUIT_SHOP_DAO = new FruitShopDaoImpl();

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(Operation.BALANCE, new BalanceOperationHandlerImpl(FRUIT_SHOP_DAO));
        operationHandlersMap.put(Operation.RETURN, new ReturnOperationHandlerImpl(FRUIT_SHOP_DAO));
        operationHandlersMap.put(Operation.SUPPLY, new SupplyOperationHandlerImpl(FRUIT_SHOP_DAO));
        operationHandlersMap.put(Operation.PURCHASE, new PurchaseOperationHandlerImpl(FRUIT_SHOP_DAO));

        List<FruitTransaction> fruitTransactionList = new ReaderFileImpl().readFile(INPUT_FILE);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        FruitTransactionToFile fruitTransactionToFile = new FruitTransactionToFileImpl(operationStrategy);
        fruitTransactionToFile.processTransaction(fruitTransactionList);

        ReportListFruit reportListFruit = new ReportListFruitImpl();
        String report = reportListFruit.createReport();

        WriteToFile write = new WriteToFileImpl();
        write.writeToFile(OUTPUT_FILE, report);
    }
}
