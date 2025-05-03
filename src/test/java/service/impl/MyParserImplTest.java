package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import model.TransactionDto;
import org.junit.BeforeClass;
import org.junit.Test;
import service.MyParser;
import service.Validator;

public class MyParserImplTest {
    private static Validator validator;
    private static MyParser<TransactionDto> parser;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        parser = new MyParserImpl(validator);
    }

    @Test
    public void parse_validData_ok() {
        List<String> inputData = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "r,apple,10",
                "p,banana,5"
        );
        List<TransactionDto> expected = List.of(
                new TransactionDto("b", "banana", 20),
                new TransactionDto("b", "apple", 100),
                new TransactionDto("s", "banana", 100),
                new TransactionDto("r", "apple", 10),
                new TransactionDto("p", "banana", 5)
        );
        List<TransactionDto> actual = parser.parseLines(inputData);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i).getFruitName(), actual.get(i).getFruitName());
            assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullValue_notOk() {
        parser.parseLines(null);
    }
}
