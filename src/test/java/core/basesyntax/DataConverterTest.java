package core.basesyntax;

import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private final List<String> properData = new ArrayList<>();

    @BeforeEach
    void setUp() {
        properData.add("type,fruit,quantity");
        properData.add("b,banana,20");
        properData.add("b,apple,100");
        properData.add("r,apple,10");
        properData.add("p,banana,5");
        properData.add("s,banana,50");
        properData.add("p,apple,22");
    }

    @Test
    void convertToTransaction_properData_ok() {
        DataConverter dataConverter = new DataConverterImpl();
        Assertions.assertDoesNotThrow(() -> dataConverter.convertToTransaction(properData));
    }

    @Test
    void convertToTransaction_differentOrder_ok() {
        properData.clear();
        properData.add("quantity,type,fruit");
        properData.add("20,b,banana");
        properData.add("100,b,apple");
        properData.add("10,r,apple");
        properData.add("5,p,banana,");
        properData.add("20,s,banana");
        properData.add("22,p,apple");
        DataConverter dataConverter = new DataConverterImpl();
        Assertions.assertDoesNotThrow(() -> dataConverter.convertToTransaction(properData));
    }

    @Test
    void convertToTransaction_wrongHeader_notOk() {
        DataConverter dataConverter = new DataConverterImpl();
        String header = properData.set(0, "typeR,fruit,quantity");
        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(properData));
    }

    @Test
    void convertToTransaction_emptyList_notOk() {
        DataConverter dataConverter = new DataConverterImpl();
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(new ArrayList<>()));
    }

    @Test
    void convertToTransaction_noHeader_notOk() {
        DataConverter dataConverter = new DataConverterImpl();
        String header = properData.remove(0);
        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(properData));
    }
}
