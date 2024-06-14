package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.FruitsFromFile;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitsFromFileImplTest {
    private FruitsFromFile fruitsFromFile;

    @BeforeEach
    void setUp() {
        fruitsFromFile = new FruitsFromFileImpl();
    }

    @Test
    void getFruitsFromFile_NullInputList_NotOk() {
        List<FruitTransaction> fruitTransactions = null;
        assertThrows(CantWorkWithThisFileException.class,
                () -> fruitsFromFile.getFruitsFromFile(fruitTransactions));
    }

    @Test
    void getFruitsFromFile_CorrectInputOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,"banana", 20));
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,"apple", 100));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,"banana", 100));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,"banana", 13));
        fruitTransactions.add(new FruitTransaction(
                Operation.RETURN,"apple", 10));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,"apple", 20));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,"banana", 5));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,"banana", 50));

        List<String> expected = new ArrayList<>();
        expected.add("banana");
        expected.add("apple");
        List<String> result = fruitsFromFile.getFruitsFromFile(fruitTransactions);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }
}
