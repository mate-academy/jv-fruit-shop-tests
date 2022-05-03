package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FruitTransactionServiceImplTest {
    FruitDao fruitDao;
    FruitTransactionService transactionService;
    List<FruitTransaction> testTransactionFruits;
    List<Fruit> fruitList;
    List<FruitTransaction> balanceList;
    List<FruitTransaction> supplyList;
    List<FruitTransaction> purchaseList;
    List<FruitTransaction> returnList;

    @Before
    public void setUp() throws Exception {
        Fruit banana = new Fruit("banana", 55);

        fruitList = List.of(new Fruit("banana", 110));

        FruitTransaction balanceOperation = new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana", 55));
        FruitTransaction supplyOperation = new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("banana", 55));
        FruitTransaction purchaseOperation = new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("banana", 55));
        FruitTransaction returnOperation = new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("banana", 55));

        balanceList = List.of(balanceOperation);
        supplyList = List.of(balanceOperation, supplyOperation);
        purchaseList = List.of(balanceOperation, purchaseOperation);
        returnList = List.of(balanceOperation, returnOperation);

        testTransactionFruits = List.of(balanceOperation, supplyOperation, purchaseOperation, returnOperation);
        fruitDao = new FruitDaoImpl();
        transactionService = new FruitTransactionServiceImpl(fruitDao);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void process_Ok() {
        transactionService.process(testTransactionFruits);
        List<Fruit> fruitDaoAll = fruitDao.getAll();

        assertEquals(fruitList, fruitDaoAll);
    }

    @Test
    public void processBalance_Ok() {
        transactionService.process(balanceList);
        List<Fruit> fruitDaoAll = fruitDao.getAll();

        assertEquals(55, fruitDaoAll.get(0).getQuantity());
    }

    @Test
    public void processSupply_Ok() {
        transactionService.process(supplyList);
        List<Fruit> fruitDaoAll = fruitDao.getAll();

        assertEquals(110, fruitDaoAll.get(0).getQuantity());
    }

    @Test
    public void processPurchase_Ok() {
        transactionService.process(purchaseList);
        List<Fruit> fruitDaoAll = fruitDao.getAll();

        assertEquals(0, fruitDaoAll.get(0).getQuantity());
    }

    @Test
    public void processReturn_Ok() {
        transactionService.process(returnList);
        List<Fruit> fruitDaoAll = fruitDao.getAll();

        assertEquals(110, fruitDaoAll.get(0).getQuantity());
    }

    @Test
    public void processEmpty_Ok() {
        transactionService.process(Collections.emptyList());
        List<Fruit> fruitDaoAll = fruitDao.getAll();

        assertEquals(fruitDaoAll, Collections.emptyList());
    }
}