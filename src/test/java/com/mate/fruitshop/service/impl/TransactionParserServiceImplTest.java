package com.mate.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import com.mate.fruitshop.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static TransactionParserServiceImpl parser = new TransactionParserServiceImpl();
    private static List<String> testInput;
    private static List<Transaction> expectedOutput;

    @Before
    public void setUp() {
        testInput = new ArrayList<>();
        testInput.add("type,fruit,quantity");
        testInput.add("b,banana,10");
        testInput.add("p,apple,5");
        testInput.add("s,banana,15");
        testInput.add("r,apple,3");
        expectedOutput = new ArrayList<>();
        expectedOutput.add(new Transaction(Transaction.Operation.BALANCE,
                "banana", 10));
        expectedOutput.add(new Transaction(Transaction.Operation.PURCHASE,
                "apple", 5));
        expectedOutput.add(new Transaction(Transaction.Operation.SUPPLY,
                "banana", 15));
        expectedOutput.add(new Transaction(Transaction.Operation.RETURN,
                "apple", 3));
    }

    @Test(expected = RuntimeException.class)
    public void parse_EmptyList_NotOk() {
        parser.parse(new ArrayList<>());
    }

    @Test
    public void parse_CorrectData_Ok() {
        List<Transaction> actual = parser.parse(testInput);
        for (int i = 0; i < testInput.size(); i++) {
            assertEquals(expectedOutput.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expectedOutput.get(i).getFruitName(), actual.get(i).getFruitName());
            assertEquals(expectedOutput.get(i).getQuantity(), actual.get(i).getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void parse_IncorrectData_NotOk() {
        testInput.add("123");
        parser.parse(testInput);
    }
}
