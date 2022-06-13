package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseStatisticService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParseStatisticServiceImplTest {
    private ParseStatisticService parseStatisticService;

    @Before
    public void setUp() throws Exception {
        parseStatisticService = new ParseStatisticServiceImpl();
    }

    @Test
    public void parse_parameterNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            parseStatisticService.parse(null);
        });
    }

    @Test
    public void parse_correctParameters_Ok() {
        FruitTransaction balanceBanana20 = new FruitTransaction();
        balanceBanana20.setOperation(FruitTransaction.Operation.BALANCE);
        balanceBanana20.setFruit("banana");
        balanceBanana20.setQuantity(20);
        FruitTransaction balanceApple100 = new FruitTransaction();
        balanceApple100.setOperation(FruitTransaction.Operation.BALANCE);
        balanceApple100.setFruit("apple");
        balanceApple100.setQuantity(100);
        FruitTransaction supplyBanana100 = new FruitTransaction();
        supplyBanana100.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyBanana100.setFruit("banana");
        supplyBanana100.setQuantity(100);
        FruitTransaction purchaseBanana13 = new FruitTransaction();
        purchaseBanana13.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseBanana13.setFruit("banana");
        purchaseBanana13.setQuantity(13);
        FruitTransaction returnApple10 = new FruitTransaction();
        returnApple10.setOperation(FruitTransaction.Operation.RETURN);
        returnApple10.setFruit("apple");
        returnApple10.setQuantity(10);
        FruitTransaction purchaseApple20 = new FruitTransaction();
        purchaseApple20.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseApple20.setFruit("apple");
        purchaseApple20.setQuantity(20);
        FruitTransaction purchaseBanana5 = new FruitTransaction();
        purchaseBanana5.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseBanana5.setFruit("banana");
        purchaseBanana5.setQuantity(5);
        FruitTransaction supplyBanana50 = new FruitTransaction();
        supplyBanana50.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyBanana50.setFruit("banana");
        supplyBanana50.setQuantity(50);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(balanceBanana20);
        expected.add(balanceApple100);
        expected.add(supplyBanana100);
        expected.add(purchaseBanana13);
        expected.add(returnApple10);
        expected.add(purchaseApple20);
        expected.add(purchaseBanana5);
        expected.add(supplyBanana50);
        List<String> list = new ArrayList<>();
        list.add("type,fruit ,quantity");
        list.add("b   ,banana,20");
        list.add("b   ,apple ,100");
        list.add("s   ,banana,100");
        list.add("p   ,banana,13");
        list.add("r   ,apple ,10");
        list.add("p   ,apple ,20");
        list.add("p   ,banana,5");
        list.add("s   ,banana,50");
        List<FruitTransaction> actual = parseStatisticService.parse(list);
        assertEquals(expected,actual);
    }
}
