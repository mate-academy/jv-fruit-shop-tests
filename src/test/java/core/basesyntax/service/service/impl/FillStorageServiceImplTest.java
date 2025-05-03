package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FillStorageService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FillStorageServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 0;
    private static final String HEADER_LINE = "type,fruit,quantity";
    private static final String VALID_BANANA_DATA = "b,banana,20";
    private static final String VALID_APPLE_DATA = "b,apple,30";
    private static final String INVALID_BANANA_DATA = "s,   apple1,20";
    private static FillStorageService fillStorageService;
    private static List<String> inputData;
    private static List<Fruit> expected;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fillStorageService = new FillStorageServiceImpl();
        inputData = new ArrayList<>();
        inputData.add(HEADER_LINE);
        inputData.add(VALID_BANANA_DATA);
        inputData.add(VALID_APPLE_DATA);
        expected = new ArrayList<>();
        expected.add(new Fruit(BANANA, DEFAULT_QUANTITY));
        expected.add(new Fruit(APPLE, DEFAULT_QUANTITY));
    }

    @Test
    public void fill_ok() {
        fillStorageService.fill(inputData);
        assertEquals(expected, Storage.getFruits());
    }

    @Test
    public void fill_incorrectDataFormat_ok() {
        inputData.add(INVALID_BANANA_DATA);
        fillStorageService.fill(inputData);
        assertEquals(expected, Storage.getFruits());
    }

    @Test
    public void fill_InputDataNull_notOk() {
        thrown.expect(RuntimeException.class);
        fillStorageService.fill(null);
    }

    @After
    public void tearDown() {
        Storage.getFruits().clear();
    }
}
