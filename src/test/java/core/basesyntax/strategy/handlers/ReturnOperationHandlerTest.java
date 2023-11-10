package core.basesyntax.strategy.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ReturnOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private StorageDao storageDao;
    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        storageDao = new FruitDao();
        returnOperationHandler = new ReturnOperationHandler(storageDao);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }





}
