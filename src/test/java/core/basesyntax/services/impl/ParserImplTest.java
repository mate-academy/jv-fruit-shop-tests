package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.models.TransactionDto;
import core.basesyntax.services.Parser;
import core.basesyntax.services.Validator;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Validator validator;
    private static Parser<TransactionDto> parser;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        parser = new ParserImpl(validator);
    }

    @Test
    public void parse_validData_Ok() {
        List<String> inputData = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<TransactionDto> expected = List.of(
                new TransactionDto("b", "banana", 20),
                new TransactionDto("b", "apple", 100),
                new TransactionDto("s", "banana", 100),
                new TransactionDto("p", "banana", 13),
                new TransactionDto("r", "apple", 10),
                new TransactionDto("p", "apple", 20),
                new TransactionDto("p", "banana", 5),
                new TransactionDto("s", "banana", 50)
        );
        List<TransactionDto> actual = parser.parse(inputData);
        assertEquals("Wrong list size.", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("Wrong operation on index " + i,
                    expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals("Wrong fruit name on index " + i,
                    expected.get(i).getFruitName(), actual.get(i).getFruitName());
            assertEquals("Wrong quantity on index " + i,
                    expected.get(i).getQuantity(), actual.get(i).getQuantity());
        }
    }

    @Test
    public void parse_emptyList_Ok() {
        List<TransactionDto> actual = parser.parse(List.of());
        assertEquals(Collections.emptyList(), actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullValue_NotOk() {
        parser.parse(null);
    }
}
