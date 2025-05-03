package core.basesyntax;

import core.basesyntax.service.parser.ParseData;
import core.basesyntax.service.parser.ParseDataImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseDataTest {
    private static final String VALID_DATA = "type,fruit,quantity\n"
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";
    private static final List<String[]> LIST_EXPECTED = new ArrayList<>();
    private static final ParseData parseData = new ParseDataImpl();

    @BeforeClass
    public static void beforeClass() {
        LIST_EXPECTED.add(new String[]{"b", "banana", "20"});
        LIST_EXPECTED.add(new String[]{"b", "apple", "100"});
        LIST_EXPECTED.add(new String[]{"s", "banana", "100"});
        LIST_EXPECTED.add(new String[]{"p", "banana", "13"});
        LIST_EXPECTED.add(new String[]{"r", "apple", "10"});
        LIST_EXPECTED.add(new String[]{"p", "apple", "20"});
        LIST_EXPECTED.add(new String[]{"p", "banana", "5"});
        LIST_EXPECTED.add(new String[]{"s", "banana", "50"});
    }

    @Test
    public void parse_validDataFormat_ok() {
        List<String[]> actual = parseData.parse(VALID_DATA);
        List<String> expectedToList = LIST_EXPECTED.stream()
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> actualToList = actual.stream()
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (int i = 0; i < actualToList.size(); i++) {
            Assert.assertEquals(actualToList.get(i), expectedToList.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void parse_emptyData_notOk() {
        parseData.parse(null);
    }
}
