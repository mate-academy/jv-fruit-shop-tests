package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionParsing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionParsingImplTest {
    private static FruitTransactionParsing fruitTransactionParsing;

    @BeforeAll
    public static void init() {
        Map<String, Operation> codeOperationMap = new HashMap<>();
        codeOperationMap.put("b", Operation.BALANCE);
        codeOperationMap.put("s", Operation.SUPPLY);
        codeOperationMap.put("p", Operation.PURCHASE);
        codeOperationMap.put("r", Operation.RETURN);

        fruitTransactionParsing = new FruitTransactionParsingImpl(codeOperationMap);
    }

    @Test
    public void parseFromString_correctData_ok() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("s,banana,100");
        list.add("r,apple,10");
        list.add("p,apple,20");

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(Operation.PURCHASE, "apple", 20));

        List<FruitTransaction> actual = fruitTransactionParsing.parse(list);

        assertEquals(expected, actual);
    }

    @Test
    public void parse_negativeQuantity_throwsException() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("s,banana,-100");
        list.add("r,apple,10");
        list.add("p,apple,20");

        assertThrows(RuntimeException.class, () -> {
            List<FruitTransaction> actual = fruitTransactionParsing.parse(list);
        });
    }

    @Test
    public void parse_wrongType_throwsException() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("g,banana,20");
        list.add("s,banana,100");
        list.add("r,apple,10");
        list.add("p,apple,20");

        assertThrows(RuntimeException.class, () -> {
            List<FruitTransaction> actual = fruitTransactionParsing.parse(list);
        });
    }

    @Test
    public void parse_wrongQuantity_throwsException() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("s,banana,100");
        list.add("r,apple,dfs");
        list.add("p,apple,20");

        assertThrows(RuntimeException.class, () -> {
            List<FruitTransaction> actual = fruitTransactionParsing.parse(list);
        });
    }

    @Test
    public void parse_wrongDataForm_throwsException() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("s,100");
        list.add("r,apple,dfs");
        list.add("p,apple,20");

        assertThrows(RuntimeException.class, () -> {
            List<FruitTransaction> actual = fruitTransactionParsing.parse(list);
        });
    }

    @Test
    public void parse_null_throwsException() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add(null);
        list.add("s,banana,-100");
        list.add("r,apple,10");
        list.add("p,apple,20");

        assertThrows(RuntimeException.class, () -> {
            List<FruitTransaction> actual = fruitTransactionParsing.parse(list);
        });
    }
}
