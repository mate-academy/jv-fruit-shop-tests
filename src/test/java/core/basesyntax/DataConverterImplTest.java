package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    @Test
    void convert_validList_returnsNonEmptyList() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> correctList = new ArrayList<>();
        correctList.add("b,banana,20");
        correctList.add("b,apple,50");
        Assertions.assertFalse(dataConverter.convertToTransaction(correctList).isEmpty());
    }

    @Test
    void convert_noSuchElement_throwsException() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> incorrectList = new ArrayList<>();
        incorrectList.add("x,banana,20");
        assertThrows(NoSuchElementException.class, () ->
                dataConverter.convertToTransaction(incorrectList));
    }
}
