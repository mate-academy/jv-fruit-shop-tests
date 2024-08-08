package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.dao.ReportDao;
import core.basesyntax.dao.impl.ReportDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.TransactionProcessImpl;
import core.basesyntax.strategy.StrategyFruitTransaction;
import core.basesyntax.strategy.impl.StrategyFruitTransactionImpl;
import core.basesyntax.transaction.OperationHandler;
import core.basesyntax.transaction.impl.PurchaseHandler;
import core.basesyntax.transaction.impl.ReturnHandler;
import core.basesyntax.transaction.impl.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionProcessImplTest {
    private static final Map<Operation, OperationHandler> operations =
            Map.of(Operation.SUPPLY, new SupplyHandler(),
                    Operation.PURCHASE, new PurchaseHandler(),
                    Operation.RETURN, new ReturnHandler());
    private static final String BANANA_FRUIT = "banana";
    private static final String APPLE_FRUIT = "apple";
    private static final String ORANGE_FRUIT = "orange";
    private static final int BANANA_BALANCE = 100;
    private static final int BANANA_QUANTITY_50 = 50;
    private static final int BANANA_QUANTITY_30 = 30;
    private static final int APPLE_QUANTITY_50 = 50;
    private static final int APPLE_QUANTITY_30 = 30;
    private static final int ORANGE_QUANTITY_50 = 50;
    private static final int ORANGE_QUANTITY_30 = 30;

    private ReportDao reportDao;
    private StrategyFruitTransaction strategyFruitTransaction;
    private TransactionProcess transactionProcess;

    @BeforeEach
    void setUp() {
        strategyFruitTransaction = new StrategyFruitTransactionImpl(operations);
        reportDao = new ReportDaoImpl();
        transactionProcess =
                new TransactionProcessImpl(strategyFruitTransaction, reportDao);
    }

    @Test
    public void process_balanceTransaction_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(Operation.BALANCE);
        balanceTransaction.setFruit(BANANA_FRUIT);
        balanceTransaction.setQuantity(BANANA_BALANCE);
        assertDoesNotThrow(() -> transactionProcess.process(balanceTransaction));
    }

    @Test
    public void process_purchaseTransaction_ok() {
        FruitTransaction applePurchase = new FruitTransaction();
        applePurchase.setOperation(Operation.PURCHASE);
        applePurchase.setFruit(APPLE_FRUIT);
        applePurchase.setQuantity(APPLE_QUANTITY_50);
        reportDao.updateReport(applePurchase);

        FruitTransaction apple = new FruitTransaction();
        apple.setOperation(Operation.PURCHASE);
        apple.setFruit(APPLE_FRUIT);
        apple.setQuantity(APPLE_QUANTITY_30);
        assertDoesNotThrow(() -> transactionProcess.process(apple));
    }

    @Test
    public void process_returnTransaction_ok() {
        FruitTransaction orangeReturn = new FruitTransaction();
        orangeReturn.setOperation(Operation.RETURN);
        orangeReturn.setFruit(ORANGE_FRUIT);
        orangeReturn.setQuantity(ORANGE_QUANTITY_50);
        reportDao.updateReport(orangeReturn);

        FruitTransaction orange = new FruitTransaction();
        orange.setOperation(Operation.RETURN);
        orange.setFruit(ORANGE_FRUIT);
        orange.setQuantity(ORANGE_QUANTITY_30);
        assertDoesNotThrow(() -> transactionProcess.process(orange));
    }

    @Test
    public void process_supplyTransaction_ok() {
        FruitTransaction bananaBalance = new FruitTransaction();
        bananaBalance.setOperation(Operation.BALANCE);
        bananaBalance.setFruit(BANANA_FRUIT);
        bananaBalance.setQuantity(BANANA_QUANTITY_50);
        reportDao.updateReport(bananaBalance);

        FruitTransaction banana = new FruitTransaction();
        banana.setOperation(Operation.BALANCE);
        banana.setFruit(BANANA_FRUIT);
        banana.setQuantity(BANANA_QUANTITY_30);
        assertDoesNotThrow(() -> transactionProcess.process(banana));
    }
}
