package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.TransactionManager;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturningOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionManagerTest {
    private static final List<String> EXPECTED_READER_RESULT = List.of(
            " type,fruit,quantity",
            "    b,banana,100",
            "    b,apple,100",
            "    s,banana,10",
            "    s,apple,10",
            "    r,banana,10",
            "    r,apple,10",
            "    p,banana,11",
            "    p,apple,11");
    private final ParserService<String> parserService = new FruitTransactionMapper();
    private final List<FruitTransaction> expectedOutAfterParsing =
            parserService.parse(EXPECTED_READER_RESULT);
    private final OperationHandler balance = new BalanceOperationHandler();
    private final OperationHandler supply = new SupplyOperationHandler();
    private final OperationHandler purchase = new PurchaseOperationHandler();
    private final OperationHandler returning = new ReturningOperationHandler();
    private final List<OperationHandler> goodOperationList = List.of(
            balance, supply, purchase, returning);
    private TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        Storage.dataBase.clear();
        OperationStrategy actualStrategy = new OperationStrategyImpl(goodOperationList);
        transactionManager = new TransactionManager(actualStrategy);
    }

    @Test
    void transactionManager_ValidData_Size2() {
        transactionManager.process(expectedOutAfterParsing);
        assertEquals(2, Storage.dataBase.size());
        assertEquals(109, Storage.dataBase.get("apple"));
    }
}
