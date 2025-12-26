package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.Test;
import service.DataConverterImpl;

public class DataConverterImplTest {
    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    public void convert_Ok() {
        List<String> test = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,20", "p,apple,20", "p,banana,5",
                "s,banana,50");
        List<FruitTransaction> result = dataConverter.convertToTransaction(test);

        assertEquals(8,result.size());
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(5,result.get(6).getQuantity());
    }

    @Test
    public void convertToTransaction_InvalidNumber_ThrowsException() {
        List<String> badInput = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,fd"
        );
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(badInput);
        });
    }

}
