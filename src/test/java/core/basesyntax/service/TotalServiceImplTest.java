package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TotalServiceImplTest {
    private static final FruitTransaction.Operation OPERATION_TOTAL =
            FruitTransaction.Operation.TOTAL;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String WORD_DELI = ",";
    private static final String NEW_LINE = System.lineSeparator();
    private TotalService totalService = new TotalServiceImpl();

    @AfterEach
    void afterEachTest() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void getReport_create_OK() {
        String expected = createStringResult();
        createFruits();
        createTotalFruits();
        String result = totalService.getReport();
        Assert.assertEquals("Wrong result string must be: "
                + expected + "\n", expected, result);
    }

    public static Map<String, List<FruitTransaction>> createFruits() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);
        List<FruitTransaction> fruitsList = new ArrayList<>();
        fruitsList.add(fruitTransaction1);
        Storage.fruitTransactions.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(20);
        fruitsList.add(fruitTransaction2);
        Storage.fruitTransactions.add(fruitTransaction2);
        Map<String, List<FruitTransaction>> fruitsMap = new HashMap<>();
        fruitsMap.put("apple", fruitsList);
        return fruitsMap;
    }

    public static FruitTransaction createTotalFruits() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(OPERATION_TOTAL);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(80);
        Storage.fruitTransactions.add(fruitTransaction);
        return fruitTransaction;
    }

    public static String createStringResult() {
        StringBuilder builder = new StringBuilder();
        builder.append(FIRST_LINE).append(NEW_LINE);
        builder.append("apple").append(WORD_DELI)
                .append("80").append(NEW_LINE);
        return builder.toString();
    }
}
