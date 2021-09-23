package core.basesyntax.fruitshop.service.operations;

import core.basesyntax.fruitshop.fruitstoragedb.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.RecordDto;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PurchaseOperationHandlerTest {

    @Before
    public void setUp() throws Exception {
        Map<Fruit,Integer> storage = FruitStorage.getStorage();
        RecordDto balanceInput = new RecordDto(OperationType.BALANCE, new Fruit("orange"), 40);
        BalanceOperationHandler balance = new BalanceOperationHandler();
        balance.applyOperation(balanceInput);
    }

    @Test(expected = RuntimeException.class)
    public void buyMoreThanAvailable_applyOperation_Ok() {
        RecordDto buy = new RecordDto(OperationType.BALANCE,  new Fruit("orange"), 50);
        PurchaseOperationHandler purchase = new PurchaseOperationHandler();
        purchase.applyOperation(buy);
    }

    @After
    public void afterEachTest() throws Exception {
        FruitStorage.getStorage().clear();
    }

   /* @Test(expected = RuntimeException.class)
    public void buyUnavailableFruit_applyOperation_Ok() {

    }*/
}