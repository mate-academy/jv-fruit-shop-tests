package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionConverterTest {
    private static List<String[]> fruitList = new ArrayList<>();
    private static TransactionConverter fruitTransaction;

    @BeforeAll
    static void fillFruitList() {
        fruitList.add(new String[]{"type", "fruit", "quantity"});
        fruitList.add(new String[]{"b", "banana", "20"});
        fruitList.add(new String[]{"b", "apple", "100"});
        fruitList.add(new String[]{"s", "banana", "100"});
        fruitList.add(new String[]{"p", "banana", "13"});
        fruitTransaction = new FruitTransactionConverter();
    }

    @Test
    public void convertToList_validInput_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"), "banana", 20));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"), "apple", 100));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("s"), "banana", 100));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"), "banana", 13));
        List<FruitTransaction> actual = fruitTransaction.convertToTransactionList(fruitList);
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }
}
