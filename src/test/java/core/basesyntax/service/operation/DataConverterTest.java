package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private DataConverter dataConverter = new DataConverterImpl();

    @Test
    void dataConverter_EmptyFruit_NotOk() {
        List<String> forData = Collections.singletonList("s,,20");
        assertThrows(RuntimeException.class, () ->
                    dataConverter.convertToTransaction(forData));
    }

    @Test
    void dataConverter_ZeroBalance_NotOk() {
        List<String> forData = Collections.singletonList("b,apple,0");
        assertThrows(RuntimeException.class, () ->
                    dataConverter.convertToTransaction(forData));
    }

    @Test
    void dataConverter_BalanceBanana_Ok() {
        List<String> forData = Collections.singletonList("b,banana,100");
        Storage.storage.put("banana", 100);
        dataConverter.convertToTransaction(forData);
    }

    @Test
    void dataConverter_ReturnBanana_Ok() {
        List<String> forData = Collections.singletonList("r,banana,50");
        Storage.storage.put("banana", 100);
        dataConverter.convertToTransaction(forData);
    }

    @Test
    void dataConverter_NotExistOperation_NotOk() {
        List<String> forData = Collections.singletonList("g,apple,10");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(forData));
    }

    @Test
    void dataConverter_FruitNameStartedNotWithLetter_NotOk() {
        List<String> forData = Collections.singletonList("r,%$*%)#%,100");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(forData));
    }
}
