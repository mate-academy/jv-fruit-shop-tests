package core.basesyntax.imp;

import core.basesyntax.service.CustomFileReader;
import org.junit.Test;

public class CustomFileReadImpTest {
    private static final String PATH_FILE = "src/test/wrong.file.csv";

    @Test(expected = RuntimeException.class)
    public void wrongPath_noOk() {
        CustomFileReader reader = new CustomFileReadImp();
        reader.readFromFile(PATH_FILE);
    }
}
