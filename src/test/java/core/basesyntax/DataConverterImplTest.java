package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_validList_returnsNonEmptyList() {
        List<String> correctList = new ArrayList<>();
        correctList.add("b,banana,20");
        correctList.add("b,apple,50");
        assertFalse(dataConverter.convertToTransaction(correctList).isEmpty());
    }

    @Test
    void convert_noSuchElement_throwsException() {
        List<String> incorrectList = new ArrayList<>();
        incorrectList.add("x,banana,20");
        assertThrows(NoSuchElementException.class, () ->
                dataConverter.convertToTransaction(incorrectList));
    }
}
