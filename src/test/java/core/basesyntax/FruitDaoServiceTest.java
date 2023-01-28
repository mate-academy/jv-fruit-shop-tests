package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.serviceimpl.FruitDaoServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoServiceTest {
    private static FruitDaoServiceImpl fruitDaoService;
    private static Storage storage;
    private static List<FruitTransaction> transactions;

    @Before
    public void setUp() {
        fruitDaoService = new FruitDaoServiceImpl();
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple",10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "bananas",120));
        Storage.fruitTransactions.clear();
    }

    @Test
    public void fruitDaoService_Add_OK() {
        List<FruitTransaction> expected = transactions;
        fruitDaoService.add(transactions);
        List<FruitTransaction> actual = Storage.fruitTransactions;
        assertEquals(expected, actual);
    }

    @Test
    public void fruitDaoService_Add_Empty_OK() {
        transactions.clear();
        List<FruitTransaction> expected = transactions;
        fruitDaoService.add(transactions);
        List<FruitTransaction> actual = Storage.fruitTransactions;
        assertEquals(expected, actual);
    }

    @Test
    public void fruitDaoService_Get_OK() {
        List<FruitTransaction> expected = transactions;
        Storage.fruitTransactions.addAll(transactions);
        List<FruitTransaction> actual = fruitDaoService.get();
        assertEquals(expected, actual);
    }

    @Test
    public void fruitDaoService_Get_Empty_OK() {
        Storage.fruitTransactions.clear();
        List<FruitTransaction> expected = transactions;
        Storage.fruitTransactions.addAll(transactions);
        List<FruitTransaction> actual = fruitDaoService.get();
        assertEquals(expected, actual);
    }
}
