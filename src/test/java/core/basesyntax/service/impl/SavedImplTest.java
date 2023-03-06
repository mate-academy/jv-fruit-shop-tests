package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Saved;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SavedImplTest {
    private static final String INFORMATION_WITH_FILE = "type,fruit,quantity///"
            + "b,banana,20///s,banana,100///p,banana,13///r,apple,10///";
    private static final String INCORRECT_OPTION_INFORMATION_WITH_FILE = "type,fruit,quantity///"
            + "b,banana,20///u,banana,100///h,banana,13///w,apple,10///";
    private static final String INCORRECT_INFORMATION_WITH_FILE = "HFJDNVDNKNVFNDVVFDMNV,";
    private static List<FruitTransaction> fruitTransactions;
    private static Saved saved;

    @BeforeClass
    public static void beforeClass() {
        saved = new SavedImpl();
        fruitTransactions = new ArrayList<>();
    }

    @Before
    public void setUp() {
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setQuantity(20);
        fruitTransaction3.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactions.add(fruitTransaction3);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(100);
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransactions.add(fruitTransaction2);
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(13);
        fruitTransaction1.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransactions.add(fruitTransaction1);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransactions.add(fruitTransaction);
    }

    @Test
    public void saved_data_isOk() {
        List<FruitTransaction> transactions = saved.saveToDb(INFORMATION_WITH_FILE);
        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(transactions.get(i).getFruit(), fruitTransactions.get(i).getFruit());
            assertEquals(transactions.get(i).getQuantity(), fruitTransactions.get(i).getQuantity());
            assertEquals(transactions.get(i).getOperation(),
                    fruitTransactions.get(i).getOperation());
        }
    }

    @Test(expected = RuntimeException.class)
    public void saved_nullData_isNotOk() {
        saved.saveToDb(null);
    }

    @Test(expected = RuntimeException.class)
    public void saved_incorrectData_isNotOk() {
        saved.saveToDb(INCORRECT_INFORMATION_WITH_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void saved_dataWithIncorrectOption_isNotOk() {
        saved.saveToDb(INCORRECT_OPTION_INFORMATION_WITH_FILE);
    }
}
