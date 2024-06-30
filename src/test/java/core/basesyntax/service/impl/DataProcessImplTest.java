package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcess;
import core.basesyntax.strategy.NegativeBalanceException;
import core.basesyntax.strategy.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DataProcessImplTest {

    private final DataProcess dataProcess = new DataProcessImpl();

    @Test
    void process_correctInput_ok() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        transactionList.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        transactionList.add(new FruitTransaction(Operation.BALANCE, "orange", 70));
        transactionList.add(new FruitTransaction(Operation.BALANCE, "apple", 30));
        transactionList.add(new FruitTransaction(Operation.PURCHASE, "apple", 10));
        transactionList.add(new FruitTransaction(Operation.PURCHASE, "orange", 15));
        transactionList.add(new FruitTransaction(Operation.SUPPLY, "banana", 30));
        transactionList.add(new FruitTransaction(Operation.RETURN, "apple", 10));

        Map<String, Integer> expectedData = new HashMap<>();
        expectedData.put("banana", 50);
        expectedData.put("orange", 55);
        expectedData.put("apple", 30);

        Map<String, Integer> actual = dataProcess.process(transactionList);
        assertEquals(expectedData, actual);
    }

    @Test
    void process_emptyInput_notOk() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        Map<String, Integer> expectedData = new HashMap<>();
        Map<String, Integer> actual = dataProcess.process(transactionList);
        assertEquals(expectedData, actual);
    }

    @Test
    void process_zeroQuantityTransaction_ok() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        transactionList.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        transactionList.add(new FruitTransaction(Operation.SUPPLY, "banana", 0));
        transactionList.add(new FruitTransaction(Operation.PURCHASE, "banana", 0));

        Map<String, Integer> expectedData = new HashMap<>();
        expectedData.put("banana", 20);

        Map<String, Integer> actual = dataProcess.process(transactionList);
        assertEquals(expectedData, actual);
    }

    @Test
    void process_minusQuantityTransaction_notOk() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        transactionList.add(new FruitTransaction(Operation.BALANCE, "apple", 20));
        transactionList.add(new FruitTransaction(Operation.PURCHASE, "apple", 30));

        assertThrows(NegativeBalanceException.class, () -> dataProcess.process(transactionList));
    }
}
