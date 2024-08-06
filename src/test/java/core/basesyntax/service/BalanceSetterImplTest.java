package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ReportDao;
import core.basesyntax.dao.impl.ReportDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.BalanceSetterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceSetterImplTest {
    private static final List<FruitTransaction> fruitsTransactions;

    static {
        fruitsTransactions = new ArrayList<>();
        FruitTransaction firstBalance = new FruitTransaction();
        firstBalance.setFruit("banana");
        firstBalance.setQuantity(20);
        firstBalance.setOperation(Operation.BALANCE);
        fruitsTransactions.add(firstBalance);
        FruitTransaction secondBalance = new FruitTransaction();
        secondBalance.setFruit("banana");
        secondBalance.setQuantity(21);
        secondBalance.setOperation(Operation.BALANCE);
        fruitsTransactions.add(secondBalance);
    }

    private BalanceSetter balanceSetter;

    @BeforeEach
    void setUp() {
        ReportDao reportDao = new ReportDaoImpl();
        balanceSetter = new BalanceSetterImpl(reportDao);
    }

    @Test
    public void setBalance_listFruitTransaction_ok() {
        assertDoesNotThrow(() -> balanceSetter.setBalance(fruitsTransactions));
    }

    @Test
    public void setBalance_nullFruitTransactions_notOk() {
        assertThrows(NullPointerException.class, () -> balanceSetter.setBalance(null));
    }
}
