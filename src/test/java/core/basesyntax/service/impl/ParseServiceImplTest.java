package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransfer;
import core.basesyntax.servise.ParseService;
import core.basesyntax.servise.ReaderService;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;
    private static ReaderService readerService;
    private static List<String> strings;
    private static final String FROM_FILE = "src/test/resources/InputFile.csv";

    @Before
    public void setUp() {
        parseService = new ParseServiceImpl();
        readerService = new ReaderServiceImpl();
        strings = readerService.readFromFile(FROM_FILE);
    }

    @Test
    public void parseFruitTransfers_NotEmptyList_IsOk() {
        assertTrue("Size is not Empty",parseService.parseFruitTransfers(strings).size() > 0);
    }

    @Test
    public void parseFruitTransfers_emptyList_notOk() {
        List<FruitTransfer> fruitTransfers =
                parseService.parseFruitTransfers(Collections.emptyList());
        assertTrue("Size is Empty", fruitTransfers.isEmpty());
    }

    @Test
    public void parseFruitTransfers_ListIsNull_notOk() {
        assertThrows("List can't be null", RuntimeException.class, () -> {
            parseService.parseFruitTransfers(null);
        });
    }

    @Test
    public void parseFruitTransfers_ListIsNotNull_IsOk() {
        assertTrue("List not null", parseService.parseFruitTransfers(strings) != null);
    }

    @Test
    public void getOperation_elementNotFound_notOk() {
        List<String> test = List.of("fruit,quantity", "m,avocado,80");
        assertThrows("No such element found", RuntimeException.class,() -> {
            parseService.parseFruitTransfers(test);
        });
    }
}
