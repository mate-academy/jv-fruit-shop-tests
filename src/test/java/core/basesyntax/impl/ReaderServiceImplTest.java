package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String NAME_EMPTY_FILE =
            "src/test/java/core/basesyntax/impl/resources/emptyFile.csv";
    private static final String FILE_NAME_TO_NULL = null;
    private static final String NAME_FILE_WITH_INFO =
            "src/test/java/core/basesyntax/impl/resources/CorrectData.csv";
    private static final ReaderService readerService = new ReaderServiceImpl();
    private static final List<String []> arrayList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        arrayList.add(new String[] {"type", "fruit", "quantity"});
        arrayList.add(new String[] {"b", "banana", "20"});
        arrayList.add(new String[] {"b", "apple", "100"});
    }

    @Test
    public void readEmptyFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                readerService.read(NAME_EMPTY_FILE));
    }

    @Test
    public void readNullFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                readerService.read(FILE_NAME_TO_NULL));
    }

    @Test
    public void readCorrectData_ok() {
        List<String> expected = arrayList.stream()
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        List<String[]> listOfArray = readerService.read(NAME_FILE_WITH_INFO);
        List<String> actual = listOfArray.stream()
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        assertEquals(expected, actual);
    }
}

