package core.basesyntax.model.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_AMOUNT = 20;
    private SupplyOperation supplyOperation;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    void supply_check_ok() {
        FruitTransaction fruitTransactionSupply = new FruitTransaction();
        fruitTransactionSupply.setName(FRUIT_NAME);
        fruitTransactionSupply.setType(FruitTransaction.Operation.SUPPLY);
        fruitTransactionSupply.setAmount(FRUIT_AMOUNT);
        supplyOperation.handle(fruitTransactionSupply);
        assertEquals(FRUIT_AMOUNT, fruitDao.get(FRUIT_NAME));
    }
}
