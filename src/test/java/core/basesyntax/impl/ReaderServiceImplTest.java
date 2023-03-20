package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReaderServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String NAME_EMPTY_FILE =
            "src/test/resources/emptyFile.csv";
    private static final String NAME_FILE_WITH_INFO =
            "src/test/resources/correctData.csv";
    private static ReaderService readerService;
    private static final List<String> VALID_LIST = List.of(
            "type", "fruit", "quantity",
            "b", "banana", "20",
            "b", "apple", "100");

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_readEmptyFile_notOk() {
        readerService.read(NAME_EMPTY_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void read_readNullFile_notOk() {
        readerService.read(null);
    }

    @Test
    public void read_readCorrectData_ok() {
        List<String[]> listOfArray = readerService.read(NAME_FILE_WITH_INFO);
        List<String> actual = listOfArray.stream()
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        assertEquals(VALID_LIST, actual);
    }
}

