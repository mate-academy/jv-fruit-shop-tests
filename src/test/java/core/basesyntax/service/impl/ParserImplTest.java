package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserImplTest {
    private TransactionParser parser = new ParserImpl();

    @Test
    void parseTransactions_correctData_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        dataFromFile.add("r,apple,10");
        dataFromFile.add("p,apple,20");
        dataFromFile.add("p,banana,5");
        dataFromFile.add("s,banana,50");
        List<FruitTransaction> expectedData = new ArrayList<>();
        expectedData.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expectedData.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expectedData.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expectedData.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));
        expectedData.add(new FruitTransaction(Operation.RETURN, "apple", 10));
        expectedData.add(new FruitTransaction(Operation.PURCHASE, "apple", 20));
        expectedData.add(new FruitTransaction(Operation.PURCHASE, "banana", 5));
        expectedData.add(new FruitTransaction(Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actualData = parser.parseAll(dataFromFile);
        assertEquals(actualData, expectedData);
    }

    @Test
    void parseTransactions_incorrectData_notOk() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("n,banana,20");
        dataFromFile.add("y,apple,100");
        dataFromFile.add("t,banana,100");
        dataFromFile.add("e,banana,13");
        dataFromFile.add("h,apple,10");
        assertThrows(RuntimeException.class, () -> parser.parseAll(dataFromFile));
    }
}