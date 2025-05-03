package core.basesyntax.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.ConvertationException;
import core.basesyntax.service.DataConvertorService;
import core.basesyntax.service.impl.DataConvertorServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConvertorServiceImplTest {
    private static final String FIRST_STRING = "first,line,one";
    private DataConvertorService dataConvertorService = new DataConvertorServiceImpl();

    @Test
    void convertData_nullList_notOk() {
        assertThrows(NullPointerException.class, () -> dataConvertorService.convertData(null));
    }

    @Test
    void convertData_emptyList_notOk() {
        List<String> list = List.of();
        assertThrows(ConvertationException.class, () -> dataConvertorService.convertData(list));
    }

    @Test
    void convertData_incorrectStrategy_notOk() {
        List<String> list = List.of(FIRST_STRING, "wrongStrategy,line,2");
        assertThrows(ConvertationException.class, () -> dataConvertorService.convertData(list));
    }

    @Test
    void convertData_incorrectQuantity_notOk() {
        List<String> list = List.of(FIRST_STRING, "b,line,wrongQuantity");
        assertThrows(NumberFormatException.class, () -> dataConvertorService.convertData(list));
    }

    @Test
    void convertData_incorrectQuantityColumn_notOk() {
        List<String> list = List.of(FIRST_STRING, "b,2");
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> dataConvertorService.convertData(list));
    }
}
