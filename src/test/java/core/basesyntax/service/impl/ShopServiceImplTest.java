package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String BANANA = "banana";
    private static final int BALANCE_QUANTITY = 20;
    private static final int RETURN_QUANTITY = 10;
    private static final int PURCHASE_QUANTITY = 5;
    private static final int SUPPLY_QUANTITY = 25;
    private static final int EXPECTED_QUANTITY = 50;

    private ShopService shopService;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        operationHandlers = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(storage));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation(storage));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation(storage));
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation(storage));
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit(BANANA);
        balanceTransaction.setQuantity(BALANCE_QUANTITY);
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit(BANANA);
        returnTransaction.setQuantity(RETURN_QUANTITY);
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit(BANANA);
        purchaseTransaction.setQuantity(PURCHASE_QUANTITY);
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit(BANANA);
        supplyTransaction.setQuantity(SUPPLY_QUANTITY);

        List<FruitTransaction> fruitTransactionList = List.of(balanceTransaction, returnTransaction,
                purchaseTransaction, supplyTransaction);

        shopService.process(fruitTransactionList);
        assertEquals(EXPECTED_QUANTITY, storage.getFruits().get(BANANA));

    }

}
