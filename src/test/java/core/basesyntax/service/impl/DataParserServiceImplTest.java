package core.basesyntax.service.impl;

import core.basesyntax.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class DataParserServiceImplTest {
    private static List<String> lines_list;
    private static DataParserService dataParserService;

    @BeforeAll
    static void beforeAll() {
        lines_list = new ArrayList<>();
        dataParserService = new DataParserServiceImpl();
    }

    @AfterEach
    void tearDown() {
        lines_list.clear();
    }

    @Test
    void parseData_parseValidData_Ok() {
        lines_list.add("b,orange,20");
        lines_list.add("r,apple,5");
        dataParserService.parseData(lines_list);
    }

    @Test
    void parseData_parseInvalidOperationData_NotOk() {
        lines_list.add("c,dummy,23");
        assertThrows(NoSuchElementException.class, () -> dataParserService.parseData(lines_list));
    }

    @Test
    void parseData_parseInValidQuantityData_NotOk() {
        lines_list.add("b,apple,dummy");
        assertThrows(NumberFormatException.class, () -> dataParserService.parseData(lines_list));

        lines_list.clear();
        lines_list.add("b,apple,-12");
        assertThrows(IllegalArgumentException.class, () -> dataParserService.parseData(lines_list));
    }

    @Test
    void parseData_parseLineWithMissingData_NotOk() {
        assertAll("Scenarios with missing data",
                () -> {
                    lines_list.add("b");
                    assertThrows(ArrayIndexOutOfBoundsException.class, () -> dataParserService.parseData(lines_list));
                    lines_list.clear();
                },
                () -> {
                    lines_list.add("b,apple");
                    assertThrows(ArrayIndexOutOfBoundsException.class, () -> dataParserService.parseData(lines_list));
                    lines_list.clear();
                }
        );
    }
}
