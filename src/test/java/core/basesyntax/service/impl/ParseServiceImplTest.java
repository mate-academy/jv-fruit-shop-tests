package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransfer;
import core.basesyntax.servise.ParseService;
import core.basesyntax.servise.ReaderService;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ParseServiceImplTest {
    private static ParseService parseService;
    private static ReaderService readerService;
    private static List<String> strings;
    private static final String FROM_FILE = "src/test/resourcesfortest/InputFile.csv";

    @Before
    public void setUp() {
        parseService = new ParseServiceImpl();
        readerService = new ReaderServiceImpl();
        strings = readerService.readFromFile(FROM_FILE);
    }

    @Test
    public void parseService_ListFruitTransferSizeIsNotEmptyIsOk() {
        Assertions.assertTrue(parseService.parseFruitTransfers(strings).size() > 0,
                "Size is not Empty");
    }

    @Test
    public void parseService_ListFruitTransferSizeIsEmptyIsNotOk() {
        List<FruitTransfer> fruitTransfers =
                parseService.parseFruitTransfers(Collections.emptyList());
        Assertions.assertTrue(fruitTransfers.isEmpty(),"Size is Empty");
    }

    @Test
    public void parseService_ListFruitTransferSizeIsNullNotOk() {
        Assertions.assertThrows(RuntimeException.class,() -> {
            parseService.parseFruitTransfers(null);
        },"List can't be null");
    }

    @Test
    public void parseService_ListFruitTransferSizeIsNotNullOk() {
        Assertions.assertTrue(parseService.parseFruitTransfers(strings) != null,
                "List not null");
    }

    @Test
    public void getOperation_elementNotFound_NotOk() {
        List<String> test = List.of("fruit,quantity","m,avocado,80");
        Assertions.assertThrows(RuntimeException.class,() -> {
            parseService.parseFruitTransfers(test);
        },"No such element found");
    }
}
