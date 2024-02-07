package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private static Parser parser;
    private static List<String> fruits;

    @BeforeAll
    static void beforeAll() {
        parser = new ParserImpl();
        fruits = new ArrayList<>();
    }

    @Test
    void parseFile_wrongData_notOk() {
        fruits.add("a,banana,10");
        assertThrows(IllegalArgumentException.class, () -> parser.parseFile(fruits));
    }

    @Test
    void parseFile_rightData_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 20));
        fruits.add("b,banana,10");
        fruits.add("s,apple,20");
        assertEquals(fruitTransactions, parser.parseFile(fruits));
    }

    @AfterEach
    void tearDown() {
        fruits.clear();
    }
}
