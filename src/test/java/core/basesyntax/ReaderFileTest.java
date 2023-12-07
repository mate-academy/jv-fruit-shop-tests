package core.basesyntax;

import core.basesyntax.service.file.actions.ReaderFile;
import core.basesyntax.service.file.actions.impl.ReaderFileImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderFileTest {
    private static final String DATA_FILE_PATH = "src/test/resources/dataFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String WRONG_FILE_PATH = "invalid/path/that/does/not/exist.csv";
    private static final List<String> EXPECTED_DATA = List
            .of("b,banana,20", "b,apple,100", "s,banana,100", "p,banana,13");
    private static final List<String> EXPECTED_EMPTY = new ArrayList<>();
    private ReaderFile reader = new ReaderFileImpl();

    @Test
    public void readerFruitShop_ioException_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> reader.readFileFruitShop(WRONG_FILE_PATH));
    }

    @Test
    public void readerFruitShop_emptyFile_Ok() {
        Assert.assertEquals(EXPECTED_EMPTY, reader.readFileFruitShop(EMPTY_FILE_PATH));
    }

    @Test
    public void readerFruitShop_dataFile_Ok() {
        Assert.assertEquals(EXPECTED_DATA, reader.readFileFruitShop(DATA_FILE_PATH));
    }
}
