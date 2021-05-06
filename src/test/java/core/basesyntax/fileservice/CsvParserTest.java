package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.ProductDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvParserTest {
    private static final List<String> TEST_LIST = new ArrayList<>(3);
    private static final List<String> INVALID_TEST_LIST = new ArrayList<>(4);
    private static final List<ProductDto> PRODUCT_DTOS = new ArrayList<>(3);
    private static final CsvParser csvParser = new CsvParser();

    @BeforeClass
    public static void beforeClass() throws Exception {
        TEST_LIST.add("b,banana,20");
        TEST_LIST.add("b,apple,100");
        TEST_LIST.add("s,banana,100");

        INVALID_TEST_LIST.add("b,banana,20");
        INVALID_TEST_LIST.add("b,apple,100");
        INVALID_TEST_LIST.add("s,banana,100");
        INVALID_TEST_LIST.add("s,banana,123,14,null");

        PRODUCT_DTOS.add(new ProductDto("b", "banana", 20));
        PRODUCT_DTOS.add(new ProductDto("b", "apple", 100));
        PRODUCT_DTOS.add(new ProductDto("s", "banana", 100));
    }

    @Test
    public void parseValidData_Ok() {
        List<ProductDto> actual = csvParser.parse(TEST_LIST);
        assertEquals(PRODUCT_DTOS, actual);
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
        List<ProductDto> actual = csvParser.parse(INVALID_TEST_LIST);
        assertEquals(PRODUCT_DTOS, actual);
    }
}
