package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.NoneExistedOperationHandler;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionManagerTest {
    private final OperationHandler balance = new BalanceOperationHandler();
    private final OperationHandler supply = new SupplyOperationHandler();
    private final OperationHandler purchase = new PurchaseOperationHandler();
    private final OperationHandler returning = new ReturningOperationHandler();
    private final ParserService<String> parserService = new FruitTransactionMapper();
    private final List<String> expectedReaderResult = List.of(
            " type,fruit,quantity",
            "    b,banana,100",
            "    b,apple,100",
            "    s,banana,10",
            "    s,apple,10",
            "    r,banana,10",
            "    r,apple,10",
            "    p,banana,11",
            "    p,apple,11");
    private final List<FruitTransaction> expectedOutAfterParsing =
            parserService.parse(expectedReaderResult);
    private final List<OperationHandler> goodOperationList = List.of(
            balance, supply, purchase, returning);
    private final OperationHandler wrongHandler = new NoneExistedOperationHandler();
    private final List<OperationHandler> wrongOperationList = List.of(
            wrongHandler, wrongHandler, wrongHandler, wrongHandler);
    private TransactionManager transactionManager;

    @BeforeAll
    static void setUp() {
        Storage.dataBase.clear();
    }

    @Test
    void transactionManager_Ok_Size2() {
        OperationStrategy actualStrategy = new OperationStrategyImpl(goodOperationList);
        transactionManager = new TransactionManager(actualStrategy);
        transactionManager.process(expectedOutAfterParsing);
        assertEquals(2, Storage.dataBase.size());
        assertEquals(109, Storage.dataBase.get("apple"));
    }

    @Test
    void transactionManager_WrongOperations_Size0() {
        OperationStrategy actualStrategy = new OperationStrategyImpl(wrongOperationList);
        transactionManager = new TransactionManager(actualStrategy);
        transactionManager.process(expectedOutAfterParsing);
        assertEquals(0, Storage.dataBase.size());
    }
}
