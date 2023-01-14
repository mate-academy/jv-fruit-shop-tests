package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.ParseDataService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseDataServiceImplTest {
    private static ParseDataService parseDataService;
    private static List<String[]> expectedList;

    @BeforeClass
    public static void beforeClass() {
        parseDataService = new ParseDataServiceImpl();
        expectedList = new ArrayList<>();
        expectedList.add(new String[]{"d", "e", "f"});
        expectedList.add(new String[]{"x", "y", "z"});
    }

    @Test
    public void parseData_addValidString_Ok() {
        String input = "a,b,c" + System.lineSeparator() + "d,e,f"
                + System.lineSeparator() + "x,y,z";
        List<String[]> actualList = parseDataService.parseData(input);
        boolean isEqual = true;
        for (int i = 0; i < actualList.size(); i++) {
            for (int k = 0; k < actualList.get(i).length; k++) {
                if (!actualList.get(i)[k].equals(expectedList.get(i)[k])) {
                    isEqual = false;
                }
            }
        }
        assertTrue(isEqual);
    }

    @Test(expected = FruitShopException.class)
    public void parseData_addInvalidString_notOk() {
        parseDataService.parseData(null);
    }

    @Test(expected = FruitShopException.class)
    public void parseData_addEmptyString_notOk() {
        parseDataService.parseData("");
    }
}
