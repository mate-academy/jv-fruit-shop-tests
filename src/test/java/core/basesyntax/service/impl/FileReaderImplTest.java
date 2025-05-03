package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void check_path_Exception() {
        String path = "";
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.readDataFromFile(path));
    }

    @Test
    void check_file_Exception() {
        String path = "src/test/resources/fake.csv";
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.readDataFromFile(path));
    }

    @Test
    void check_path_Null_Exception() {
        String path = null;
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.readDataFromFile(path));
    }

    @Test
    void check_List_OK() {
        String path = "src/test/resources/dataFruit.csv";
        List<String> result = fileReader.readDataFromFile(path);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("b,banana,500", result.get(0));
    }
}
