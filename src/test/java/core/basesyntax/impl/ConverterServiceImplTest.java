package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ConverterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConverterServiceImplTest {
    private static final String[] head = {
            "fruits", "quantity\r\n"
    };
    private static ConverterService converterService;

    @BeforeAll
    static void beforeAll() {
        converterService = new ConverterServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void converter_convertList_Ok() {
        Storage.fruits.put("banana", 152);
        List<String[]> expected = new ArrayList<>();
        String[] value = {"banana", "152\r\n"};
        expected.add(head);
        expected.add(value);
        List<String[]> actual = converterService.convertList();
        Assertions.assertTrue(expected.retainAll(actual));
    }

    @Test
    public void converter_emptyStorage_notOk() {
        List<String[]> expected = new ArrayList<>();
        expected.add(head);
        List<String[]> actual = converterService.convertList();
        Assertions.assertTrue(expected.retainAll(actual));
    }
}
