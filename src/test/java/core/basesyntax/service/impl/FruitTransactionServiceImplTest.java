package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private FruitDao fruitDao;
    private FruitTransactionService transactionService;
    private List<FruitTransaction> testTransactionFruits;
    private List<Fruit> fruitList;
    private List<FruitTransaction> balanceList;
    private List<FruitTransaction> supplyList;
    private List<FruitTransaction> purchaseList;
    private List<FruitTransaction> returnList;
    private FruitTransaction tetstBalanceOperation;
    private FruitTransaction testSupplyOperation;
    private FruitTransaction testPurchaseOperation;
    private FruitTransaction testReturnOperation;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        transactionService = new FruitTransactionServiceImpl(fruitDao);
        fruitList = List.of(new Fruit("banana", 110));

        tetstBalanceOperation = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana", 55));

        testSupplyOperation = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana", 55));

        testPurchaseOperation = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana", 55));

        testReturnOperation = new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("banana", 55));

        balanceList = List.of(tetstBalanceOperation);
        supplyList = List.of(tetstBalanceOperation, testSupplyOperation);
        purchaseList = List.of(tetstBalanceOperation, testPurchaseOperation);
        returnList = List.of(tetstBalanceOperation, testReturnOperation);

        testTransactionFruits = List.of(tetstBalanceOperation,
                testSupplyOperation, testPurchaseOperation, testReturnOperation);
    }

    @After
    public void tearDown() {
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
