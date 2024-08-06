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
        balanceTransaction.setFruit("banana");
        balanceTransaction.setQuantity(100);
        assertDoesNotThrow(() -> transactionProcess.process(balanceTransaction));
    }

    @Test
    public void process_purchaseTransaction_ok() {
        FruitTransaction applePurchase = new FruitTransaction();
        applePurchase.setOperation(Operation.PURCHASE);
        applePurchase.setFruit("applePurchase");
        applePurchase.setQuantity(50);
        reportDao.updateReport(applePurchase);

        FruitTransaction apple = new FruitTransaction();
        apple.setOperation(Operation.PURCHASE);
        apple.setFruit("applePurchase");
        apple.setQuantity(30);
        assertDoesNotThrow(() -> transactionProcess.process(apple));
    }

    @Test
    public void process_returnTransaction_ok() {
        FruitTransaction orangeReturn = new FruitTransaction();
        orangeReturn.setOperation(Operation.RETURN);
        orangeReturn.setFruit("orangeReturn");
        orangeReturn.setQuantity(50);
        reportDao.updateReport(orangeReturn);

        FruitTransaction orange = new FruitTransaction();
        orange.setOperation(Operation.RETURN);
        orange.setFruit("orangeReturn");
        orange.setQuantity(30);
        assertDoesNotThrow(() -> transactionProcess.process(orange));
    }

    @Test
    public void process_supplyTransaction_ok() {
        FruitTransaction bananaBalance = new FruitTransaction();
        bananaBalance.setOperation(Operation.BALANCE);
        bananaBalance.setFruit("banana");
        bananaBalance.setQuantity(50);
        reportDao.updateReport(bananaBalance);

        FruitTransaction banana = new FruitTransaction();
        banana.setOperation(Operation.BALANCE);
        banana.setFruit("banana");
        banana.setQuantity(30);
        assertDoesNotThrow(() -> transactionProcess.process(banana));
    }
}
