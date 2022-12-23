package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderServiceImplTest {
    private static final String FILE_NAME_IS_EMPTY = "";
    private static final String EMPTY_DATABASE_FILE_NAME = "src/test/resources/emptydatabase.csv";
    private static final String DATABASE_FILE_NAME = "src/test/resources/database.csv";
    private static final List<String> FULL_DATABASE = new ArrayList<>(
            List.of("type,fruit,quantity",
                    "b,banana,20",
                    "b,apple,100",
                    "s,banana,100",
                    "p,banana,13",
                    "r,apple,10",
                    "p,apple,20",
                    "p,banana,5",
                    "s,banana,50"));
    @Rule
    public final ExpectedException readFromFileExceptionRule = ExpectedException.none();
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readFromFile_emptyFileName_notOk() {
        readFromFileExceptionRule.expect(RuntimeException.class);
        readFromFileExceptionRule.expectMessage("Can't read data from file: "
                + FILE_NAME_IS_EMPTY);
        fileReaderService.readFromFile(FILE_NAME_IS_EMPTY);
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_fileNameIsNull_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test
    public void readFromFile_emptyDataBase_Ok() {
        assertEquals(Collections.emptyList(),
                fileReaderService.readFromFile(EMPTY_DATABASE_FILE_NAME));
    }

    @Test
    public void readFromFile_fullDataBase_Ok() {
        assertEquals(FULL_DATABASE, fileReaderService.readFromFile(DATABASE_FILE_NAME));
    }
}
