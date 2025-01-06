package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String FRUIT = "apple";
    private static final int BALANCE = 100;
    private static final int SUPPLY = 20;
    private static final int PURCHASE = 10;
    private static final int RETURN = 5;
    private static final int RESULT = 115;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
    private ShopService shopService = new ShopServiceImpl(operationStrategy);
    private FruitTransaction fruitTransactionBalance = new FruitTransaction();
    private FruitTransaction fruitTransactionSupply = new FruitTransaction();
    private FruitTransaction fruitTransactionPurchase = new FruitTransaction();
    private FruitTransaction fruitTransactionReturn = new FruitTransaction();
    private FruitTransaction fruitTransactionResult = new FruitTransaction();
    private List<FruitTransaction> fruitTransactionList = new ArrayList<>();
    private List<FruitTransaction> calculatedFruitTransactionList = new ArrayList<>();

    @BeforeEach
    void before() {
        operationHandlers.clear();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        fruitTransactionBalance.setFruit(FRUIT);
        fruitTransactionSupply.setFruit(FRUIT);
        fruitTransactionPurchase.setFruit(FRUIT);
        fruitTransactionReturn.setFruit(FRUIT);
        fruitTransactionResult.setFruit(FRUIT);

        fruitTransactionBalance.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionSupply.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransactionPurchase.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransactionReturn.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransactionResult.setOperation(FruitTransaction.Operation.BALANCE);

        fruitTransactionBalance.setQuantity(BALANCE);
        fruitTransactionSupply.setQuantity(SUPPLY);
        fruitTransactionPurchase.setQuantity(PURCHASE);
        fruitTransactionReturn.setQuantity(RETURN);
        fruitTransactionResult.setQuantity(RESULT);

        fruitTransactionList.add(fruitTransactionBalance);
        fruitTransactionList.add(fruitTransactionSupply);
        fruitTransactionList.add(fruitTransactionPurchase);
        fruitTransactionList.add(fruitTransactionReturn);

        calculatedFruitTransactionList.add(fruitTransactionResult);
    }

    @Test
    void process_Ok() {
        assertEquals(calculatedFruitTransactionList, shopService.process(fruitTransactionList));
    }
}
