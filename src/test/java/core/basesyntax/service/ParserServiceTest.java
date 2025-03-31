package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.implementation.ParserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ParserServiceTest {
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }


    @Test
    void parseTransactions_stringListNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> parserService.parseTransactions(null));
    }

    @Test
    void parseTransactions_stringListEmpty_ok() {
        Assertions.assertTrue(parserService.parseTransactions(new ArrayList<>()).isEmpty());
    }

    @Test
    void parseTransactions_stringListWithWrongData_notOk() {
        List<String> wrongStringList = new ArrayList<>();
        wrongStringList.add("fruit,quantity");
        wrongStringList.add("banana,15");
        wrongStringList.add("apple,10");
        Assertions.assertThrows(RuntimeException.class,
                () -> parserService.parseTransactions(wrongStringList));
    }

    @Test
    void parseTransactions_stringListWithoutSomeData_notOk() {
        List<String> stringListWithoutSomeData = new ArrayList<>();
        stringListWithoutSomeData.add("type,fruit,quantity");
        stringListWithoutSomeData.add(" ,banana,20");
        stringListWithoutSomeData.add("s,,10");
        stringListWithoutSomeData.add("r,apple,");
        Assertions.assertThrows(RuntimeException.class,
                () -> parserService.parseTransactions(stringListWithoutSomeData));
    }

    @Test
    void parseTransactions_stringListWithoutNumber_notOk() {
        List<String> stringListWithoutNumber = new ArrayList<>();
        stringListWithoutNumber.add("type,fruit,quantity");
        stringListWithoutNumber.add("b,banana,bbb");
        stringListWithoutNumber.add("s,apple,a");
        Assertions.assertThrows(NumberFormatException.class,
                () -> parserService.parseTransactions(stringListWithoutNumber));
    }

    private List<String> initStringList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,20");
        stringList.add("b,apple,100");
        stringList.add("s,banana,100");
        stringList.add("p,apple,20");
        stringList.add("r,apple,8");
        return stringList;
    }

    private List<FruitTransaction> initFruitList() {
        List<FruitTransaction> fruitList = new ArrayList<>();
        fruitList.add(new FruitTransaction(Operation.getByType("b"), "banana", 20));
        fruitList.add(new FruitTransaction(Operation.getByType("b"), "apple", 100));
        fruitList.add(new FruitTransaction(Operation.getByType("s"), "banana", 100));
        fruitList.add(new FruitTransaction(Operation.getByType("p"), "apple", 20));
        fruitList.add(new FruitTransaction(Operation.getByType("r"), "apple", 8));
        return fruitList;
    }
}