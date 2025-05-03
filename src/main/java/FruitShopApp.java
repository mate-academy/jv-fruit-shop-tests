import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.initializer.InitializerImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.impl.BalanceOperationImpl;
import core.basesyntax.service.operation.impl.PurchaseOperationImpl;
import core.basesyntax.service.operation.impl.ReturnOperationImpl;
import core.basesyntax.service.operation.impl.SupplyOperationImpl;
import java.util.HashMap;
import java.util.Map;

public class FruitShopApp {
    private static final String REPORT_PATH
            = "src/main/resources/report.csv";
    private static final String FILE_PATH
            = "input-file.csv";

    public static void main(String[] args) {
        FruitDao<String, Integer> fruitDao = new FruitDaoImpl(new Storage());
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap
                .put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl(fruitDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl(fruitDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl(fruitDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitService fruitService = new FruitServiceImpl(fruitDao, REPORT_PATH);
        InitializerImpl initializerImpl = new InitializerImpl(operationStrategy);
        initializerImpl.initStorage(FILE_PATH);
        fruitService.createReport();
    }
}
