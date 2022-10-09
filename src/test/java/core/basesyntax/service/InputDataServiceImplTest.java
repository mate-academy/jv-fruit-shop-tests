package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.Operation;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class InputDataServiceImplTest {
    @Test
    public void getFromString_validLine_ok() {
        InputDataService inputDataService = new InputDataServiceImpl();
        String validLineOne = "b,pineapple,10";
        String validLineTwo = "r,banana,100";
        String validLineThree = "p,orange,50";
        FruitTransaction fruitTransactionOne =
                new FruitTransaction(Operation.BALANCE, "pineapple", 10);
        FruitTransaction fruitTransactionTwo =
                new FruitTransaction(Operation.RETURN,"banana", 100);
        FruitTransaction fruitTransactionThree =
                new FruitTransaction(Operation.PURCHASE, "orange", 50);

        List<String> oneItemCheck = List.of(validLineOne);
        List<FruitTransaction> fruitTransactionList = inputDataService.stringToFruitTransactionConverter(oneItemCheck);
        assertTrue(fruitTransactionList.get(0).);

    }

    @Override
    public boolean equals(FruitTransaction fruitTransaction) {
        return super.equals(obj);
    }
}
