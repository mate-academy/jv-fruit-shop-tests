package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ConverterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConverterServiceImplTest {
    private static final String[] head = {
            "fruits", "quantity\r\n"
    };
    private static ConverterService converterService;

    @BeforeClass
    public static void beforeAll() {
        converterService = new ConverterServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void converter_convertList_Ok() {
        Storage.fruits.put("banana", 152);
        List<String[]> expected = new ArrayList<>();
        String[] value = {"banana", "152\r\n"};
        expected.add(head);
        expected.add(value);
        List<String[]> actual = converterService.convertList();
        Assert.assertTrue(expected.retainAll(actual));
    }

    @Test
    public void converter_emptyStorage_notOk() {
        List<String[]> expected = new ArrayList<>();
        expected.add(head);
        List<String[]> actual = converterService.convertList();
        Assert.assertTrue(expected.retainAll(actual));
    }
}
