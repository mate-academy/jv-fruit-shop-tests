package core.basesyntax.shop.strategy;

import core.basesyntax.shop.service.Writer;
import core.basesyntax.shop.service.impl.WriterToCsvFile;
import org.junit.Assert;
import org.junit.Test;

public class FileWriteStrategyTest {
    private static final String SUPPORTED_FILETYPE = "src/test/resources//correct.csv";
    private static final String UNSUPPORTED_FILETYPE = "src/test/resources//correct.txt";

    @Test
    public void chooseWriteFileFormat_writeToSupportedFileType_ok() {
        Writer actual = FileWriteStrategy.chooseWriteFileFormat(SUPPORTED_FILETYPE);
        Assert.assertEquals(actual.getClass(), WriterToCsvFile.class);
    }

    @Test
    public void chooseWriteFileFormat_writeToUnsupportedFileType_notOk() {
        try {
            FileWriteStrategy.chooseWriteFileFormat(UNSUPPORTED_FILETYPE);
        } catch (Exception e) {
            return;
        }
        Assert.fail("Unsupported file type: " + UNSUPPORTED_FILETYPE
                .replaceAll("(^.*(\\w{3,4})$)", "$2")
                + ". Exception should be thrown");
    }
}
