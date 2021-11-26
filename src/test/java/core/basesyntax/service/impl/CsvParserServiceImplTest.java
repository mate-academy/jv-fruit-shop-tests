package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.ParserService;
import core.basesyntax.exception.ValidatorServiceException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvParserServiceImplTest {
    private static ParserService<TransactionDto> csvParserService;
    private static List<String> list;
    private static List<TransactionDto> expectedList;

    @BeforeClass
    public static void beforeClass() {
        csvParserService = new CsvParserServiceImpl(new ValidatorServiceImpl());
        expectedList = new ArrayList<>();
        expectedList.add(new TransactionDto("b", "banana", 20));
        expectedList.add(new TransactionDto("b", "apple", 100));
        expectedList.add(new TransactionDto("s", "banana", 100));
        expectedList.add(new TransactionDto("p", "banana", 13));
        expectedList.add(new TransactionDto("r", "apple", 10));
    }

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("b,apple,100");
        list.add("s,banana,100");
        list.add("p,banana,13");
        list.add("r,apple,10");
    }

    @Test(expected = ValidatorServiceException.class)
    public void parse_notValidData_notOk() {
        list.add("t,,-34");
        csvParserService.parse(list);
    }

    @Test
    public void parse_validData_ok() {
        assertEquals(expectedList, csvParserService.parse(list));
    }
}
