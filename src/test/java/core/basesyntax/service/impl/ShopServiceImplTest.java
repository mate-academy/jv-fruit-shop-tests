package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final String FRUIT = "banana";
    private static final int BALANCE_QUANTITY = 100;
    private static final int SUPPLY_QUANTITY = 50;
    private static final int PURCHASE_QUANTITY = 30;

    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        OperationStrategy operationStrategy = new OperationStrategyImpl();
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    public void processTransactions_validTransactions_ok() {
        List<FruitTransaction> transactions = buildTransactions();
        shopService.process(transactions);
    }

    private List<FruitTransaction> buildTransactions() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit(FRUIT);
        transaction1.setQuantity(BALANCE_QUANTITY);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction2.setFruit(FRUIT);
        transaction2.setQuantity(SUPPLY_QUANTITY);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction3.setFruit(FRUIT);
        transaction3.setQuantity(PURCHASE_QUANTITY);

        return List.of(transaction1, transaction2, transaction3);
    }
}
