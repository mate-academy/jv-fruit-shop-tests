package core.basesyntax.serviceimpl.readerimpl;

import core.basesyntax.strategy.serviceintrface.reader.Reader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReaderImplTest {
    private Reader reader = new ReaderImpl();

    @Test
    void check_path_Exception() {
        String path = "";
        Assertions.assertThrows(RuntimeException.class, () -> reader.readDataFromFile(path));
    }

    @Test
    void check_file_Exception() {
        String path = "src/test/resources/fake.csv";
        Assertions.assertThrows(RuntimeException.class, () -> reader.readDataFromFile(path));
    }

    @Test
    void check_path_Null_Exception() {
        String path = null;
        Assertions.assertThrows(RuntimeException.class, () -> reader.readDataFromFile(path));
    }

    @Test
    void check_List_OK() {
        String path = "src/test/resources/dataFruit.csv";
        List<String> result = reader.readDataFromFile(path);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("b,banana,500", result.get(0));
    }
}
