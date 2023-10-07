package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.operation.BaseForOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    protected static final FruitDao DAO = new FruitDaoImpl();

    protected static final Map<Operation, OperationHandler> operationStrategies
            = BaseForOperation.getOperationStrategies();

    @BeforeEach
    void beforeEachTest() {
        Storage.fruitTransactions.clear();
        Storage.fruitBalance.clear();
    }
}
