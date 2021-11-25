package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ValidatorServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public void parseNotValidData_notOk() {
        list.add("t,,-34");
        csvParserService.parse(list);
    }

    @Test
    public void parse_ok() {
        assertTrue(equalsLists(expectedList, csvParserService.parse(list)));
    }

    private boolean equalsLists(List<TransactionDto> expected, List<TransactionDto> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }
        for (int i = 0; i < actual.size(); i++) {
            TransactionDto currentExpected = expected.get(i);
            TransactionDto currentActual = actual.get(i);
            if (Objects.equals(currentExpected.getQuantity(), currentActual.getQuantity())
                    && Objects.equals(currentExpected.getFruitName(), currentActual.getFruitName())
                    && Objects.equals(currentExpected.getOperation(),
                    currentActual.getOperation())) {
                continue;
            }
            return false;
        }
        return true;
    }
}
