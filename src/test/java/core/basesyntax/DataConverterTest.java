package core.basesyntax;

import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final List<String> inversedData = new ArrayList<>();
    private static DataConverterImpl dataConverter;
    private final List<String> properData = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
        inversedData.add("quantity,type,fruit");
        inversedData.add("20,b,banana");
        inversedData.add("100,b,apple");
        inversedData.add("10,r,apple");
        inversedData.add("5,p,banana,");
        inversedData.add("20,s,banana");
        inversedData.add("22,p,apple");
    }

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
        Assertions.assertDoesNotThrow(() -> dataConverter.convertToTransaction(properData));
    }

    @Test
    void convertToTransaction_differentOrder_ok() {
        Assertions.assertDoesNotThrow(() -> dataConverter.convertToTransaction(inversedData));
    }

    @Test
    void convertToTransaction_wrongHeader_notOk() {
        String header = properData.set(0, "typeR,fruit,quantity");
        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(properData));
    }

    @Test
    void convertToTransaction_emptyList_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(new ArrayList<>()));
    }

    @Test
    void convertToTransaction_noHeader_notOk() {
        String header = properData.remove(0);
        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(properData));
    }

    @Test
    void convertToTransaction_unconvertableQuantity_notOk() {
        properData.add("b,banana, abc");
        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(properData));
    }
}
