package core.basesyntax.service.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader = new FileReaderImpl();
    private final static String FILE_PATH = "src/test/resources/reportToRead.csv";
    private List<String> fileTextList = new ArrayList<>();
    private final static String TEXT1 = "b,banana,20";
    private final static String TEXT2 = "b,apple,100";

    @Test
    void read_Ok() {
        fileTextList.clear();
        fileTextList.add(TEXT1);
        fileTextList.add(TEXT2);
        assertEquals(fileTextList, fileReader.read(FILE_PATH));
    }

    @Test
    void read_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(
                        "src/test/resources/ohBoyImTired.csv"));
    }
}
