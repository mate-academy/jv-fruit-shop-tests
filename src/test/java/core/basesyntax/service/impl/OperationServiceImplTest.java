package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.MapInitializer;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;
import core.basesyntax.strategy.OperationPerformerStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static OperationService service;

    @BeforeClass
    public static void setUp() {
        StorageDao dao = new StorageDaoImpl();
        service = new OperationServiceImpl(
                new OperationPerformerStrategyImpl(new MapInitializer(dao).initMap()),
                dao);
    }

    @Test
    public void processOperations_shouldSupply_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        Fruit banana = new Fruit("banana");
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, banana, 100));
        list.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, banana, 100));
        list.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, banana, 100));
        list.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, banana, 100));
        Integer expected = 400;
        Integer actual = service.processOperations(list).get(banana);
        assertEquals(expected, actual);
    }

    @Test
    public void processOperations_shouldPurchase_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, banana, 600));
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, apple, 100));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, banana, 550));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 99));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, banana, 30));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 1));
        assertTrue(service.processOperations(list)
                .values()
                .containsAll(List.of(20, 0)));
    }

    @Test
    public void processOperations_shouldReturn_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, banana, 600));
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, apple, 100));
        list.add(new FruitTransaction(FruitTransaction.Operation.RETURN, banana, 60));
        list.add(new FruitTransaction(FruitTransaction.Operation.RETURN, apple, 99));
        list.add(new FruitTransaction(FruitTransaction.Operation.RETURN, banana, 6));
        list.add(new FruitTransaction(FruitTransaction.Operation.RETURN, apple, 1));
        assertTrue(service.processOperations(list)
                .values()
                .containsAll(List.of(200, 666)));
    }

    @Test(expected = RuntimeException.class)
    public void processOperations_purchaseGreaterThenBalance_NotOk() {
        List<FruitTransaction> list = new ArrayList<>();
        Fruit banana = new Fruit("banana");
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, banana, 100));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, banana, 101));
        service.processOperations(list);
    }

    @Test(expected = NullPointerException.class)
    public void processOperations_invalidOperation_NotOk() {
        Fruit banana = new Fruit("banana");
        List<FruitTransaction> list = List.of(new FruitTransaction(null, banana, 100));
        service.processOperations(list);
    }

}
