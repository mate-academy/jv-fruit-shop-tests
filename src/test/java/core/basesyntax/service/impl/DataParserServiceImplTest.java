package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserServiceImplTest {
    private static List<String> linesList;
    private static DataParserService dataParserService;

    @BeforeAll
    static void beforeAll() {
        linesList = new ArrayList<>();
        dataParserService = new DataParserServiceImpl();
    }

    @AfterEach
    void tearDown() {
        linesList.clear();
    }

    @Test
    void parseData_validData_ok() {
        linesList.add("b,orange,20");
        linesList.add("r,apple,5");
        dataParserService.parseData(linesList);
    }

    @Test
    void parseData_invalidOperationData_notOk() {
        linesList.add("c,dummy,23");
        assertThrows(NoSuchElementException.class, () -> dataParserService.parseData(linesList));
    }

    @Test
    void parseData_inValidQuantityData_notOk() {
        linesList.add("b,apple,dummy");
        assertThrows(NumberFormatException.class, () -> dataParserService.parseData(linesList));

        linesList.clear();
        linesList.add("b,apple,-12");
        assertThrows(IllegalArgumentException.class, () -> dataParserService.parseData(linesList));
    }

    @Test
    void parseData_lineWithMissingData_notOk() {
        assertAll("Scenarios with missing data",
                () -> {
                    linesList.add("b");
                    assertThrows(ArrayIndexOutOfBoundsException.class,
                            () -> dataParserService.parseData(linesList));
                    linesList.clear();
                },
                () -> {
                    linesList.add("b,apple");
                    assertThrows(ArrayIndexOutOfBoundsException.class,
                            () -> dataParserService.parseData(linesList));
                    linesList.clear();
                }
        );
    }
}
