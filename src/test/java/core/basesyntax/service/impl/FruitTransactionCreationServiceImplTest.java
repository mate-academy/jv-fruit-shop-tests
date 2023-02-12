package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.FruitTransactionCreationService;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class FruitTransactionCreationServiceImplTest {
    private final FruitDao dao = new FruitDaoImpl();
    private final FruitTransactionCreationService transactionCreationService
            = new FruitTransactionCreationServiceImpl(dao);
    private List<String[]> transactionsList;

    @Before
    public void setUp() {
        transactionsList = new ArrayList<>();
        transactionsList.add(new String[]{"b", "banana", "20"});
        transactionsList.add(new String[]{"b", "apple", "100"});
        transactionsList.add(new String[]{"s", "banana", "100"});
        transactionsList.add(new String[]{"p", "banana", "13"});
        transactionsList.add(new String[]{"r", "apple", "10"});
        transactionsList.add(new String[]{"p", "apple", "20"});
        transactionsList.add(new String[]{"p", "banana", "5"});
        transactionsList.add(new String[]{"s", "banana", "50"});
    }

    @Test
    public void createTransactions_Ok() {
        transactionCreationService.createTransactions(transactionsList);
    }

    @Test(expected = RuntimeException.class)
    public void checkEmptyList_Ok() {
        transactionCreationService.createTransactions(new ArrayList<>());
    }
}