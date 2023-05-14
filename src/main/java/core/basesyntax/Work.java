package core.basesyntax;

import core.basesyntax.dao.csv.CsvFileHandlerDao;
import core.basesyntax.dao.csv.impl.CsvFileHandlerDaoImpl;
import core.basesyntax.dao.storage.FruitStorageDao;
import core.basesyntax.dao.storage.impl.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopServiceImpl;
import core.basesyntax.service.fruitshop.FruitShopService;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.impl.BalanceOperationHandler;
import core.basesyntax.service.operation.impl.PurchaseOperationHandler;
import core.basesyntax.service.operation.impl.ReturnOperationHandler;
import core.basesyntax.service.operation.impl.SupplyOperationHandler;
import core.basesyntax.strategy.FruitShopStrategy;
import core.basesyntax.strategy.impl.FruitShopStrategyImpl;
import java.util.List;
import java.util.Map;

public class Work {
    public static final String READ_FILE_PATH = "src/main/java/core/basesyntax/csv/database.csv";
    public static final String WRITE_FILE_PATH = "src/main/java/core/basesyntax/csv/report.csv";

    public static void main(String[] args) {
        Storage storage = new Storage();
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl(storage);
        CsvFileHandlerDao csvFileHandlerDao = new CsvFileHandlerDaoImpl();
        FruitShopService fruitShopService = new FruitShopServiceImpl(
                fruitStorageDao, csvFileHandlerDao);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitShopService),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(fruitShopService),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(fruitShopService),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(fruitShopService));
        FruitShopStrategy fruitShopStrategy = new FruitShopStrategyImpl(operationHandlerMap);
        List<FruitTransaction> fruitTransactionList =
                fruitShopService.readAllFromCsv(READ_FILE_PATH);

        startWork(fruitTransactionList, fruitShopStrategy);
        fruitShopService.exportReport(WRITE_FILE_PATH);
    }

    public static void startWork(List<FruitTransaction> fruitTransactionList,
                                 FruitShopStrategy fruitShopStrategy) {
        fruitTransactionList.forEach(fruitTransaction -> fruitShopStrategy
                .get(fruitTransaction.getOperation())
                .operation(fruitTransaction.getFruit(), fruitTransaction.getQuantity()));
    }
}
