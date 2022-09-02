package core.basesyntax.service;

import org.junit.Test;

public class WriterServiceImplTest {
    @Test
    public void write_() {
    }

    @Test
    public void write_correctFilePath_ok() {
        String stringToWrite = "type,fruit,quantity\n" + System.lineSeparator()
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                +"p,banana,5\n"
                + "s,banana,50"
    }
}
