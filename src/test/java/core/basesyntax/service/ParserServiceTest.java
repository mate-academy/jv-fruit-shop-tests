package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private static ParserService parserService;
    private static List<FruitTransaction> transactionList;
    private static final List<String> NULL_VALUE = null;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
        transactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple", 20));
    }

    @Test
    void parse_NullData_NotOk() {
        assertThrows(RuntimeException.class, () -> parserService.parse(NULL_VALUE));
    }

    @Test
    void parse_correctData_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("r,apple,10");
        dataFromFile.add("p,apple,20");
        List<FruitTransaction> actual = parserService.parse(dataFromFile);
        assertEquals(transactionList, actual);
    }

}
