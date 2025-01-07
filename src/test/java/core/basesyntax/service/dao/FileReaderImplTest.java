package core.basesyntax.service.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String FILE_PATH = "src/test/resources/reportToRead.csv";
    private static final String TEXT1 = "b,banana,20";
    private static final String TEXT2 = "b,apple,100";
    private FileReader fileReader = new FileReaderImpl();
    private List<String> fileTextList = new ArrayList<>();

    @Test
    void read_Ok() {
        fileTextList.clear();
        fileTextList.add(TEXT1);
        fileTextList.add(TEXT2);
        assertEquals(fileTextList, fileReader.formattedRead(FILE_PATH));
    }

    @Test
    void read_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.formattedRead(
                        "src/test/resources/ohBoyImTired.csv"));
    }
}
