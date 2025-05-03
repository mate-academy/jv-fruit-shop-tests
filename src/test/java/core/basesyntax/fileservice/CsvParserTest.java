package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.ProductDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvParserTest {
    private static final List<String> testList = new ArrayList<>(3);
    private static final List<String> invalidTestList = new ArrayList<>(4);
    private static final List<ProductDto> products = new ArrayList<>(3);
    private static final CsvParser csvParser = new CsvParser();

    @BeforeClass
    public static void beforeClass() throws Exception {
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        testList.add("s,banana,100");

        invalidTestList.add("b,banana,20");
        invalidTestList.add("b,apple,100");
        invalidTestList.add("s,banana,100");
        invalidTestList.add("s,banana,123,14,null");

        products.add(new ProductDto("b", "banana", 20));
        products.add(new ProductDto("b", "apple", 100));
        products.add(new ProductDto("s", "banana", 100));
    }

    @Test
    public void parseValidData_Ok() {
        List<ProductDto> actual = csvParser.parse(testList);
        assertEquals(products, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseEmptyList_NotOk() {
        csvParser.parse(Collections.EMPTY_LIST);
    }

    @Test(expected = RuntimeException.class)
    public void parseNullList_NotOk() {
        csvParser.parse(null);
    }

    @Test
    public void parseDataWithInvalidElement() {
        List<ProductDto> actual = csvParser.parse(invalidTestList);
        assertEquals(products, actual);
    }
}
