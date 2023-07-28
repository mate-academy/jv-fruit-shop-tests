package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.operations.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static TransactionParser transactionParser;
    private static List<String> dataFromFile;
    private static List<FruitTransaction> expectedList;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
        expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple", 60)));
        expectedList.add(new FruitTransaction(Operation.BALANCE, new Fruit("banana", 50)));
        expectedList.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 100)));
        expectedList.add(new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 50)));
        expectedList.add(new FruitTransaction(Operation.RETURN, new Fruit("apple", 30)));
        expectedList.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 200)));
        dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,apple,60");
        dataFromFile.add("b,banana,50");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,50");
        dataFromFile.add("r,apple,30");
        dataFromFile.add("s,banana,200");
    }

    @Test
    void inputParseData_correctParsingToList_Ok() {
        List<FruitTransaction> actualList = transactionParser.parseTransactions(dataFromFile);
        assertEquals(expectedList, actualList);
    }
}
