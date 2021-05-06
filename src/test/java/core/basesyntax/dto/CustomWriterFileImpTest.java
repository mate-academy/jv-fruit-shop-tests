package core.basesyntax.dto;

import core.basesyntax.imp.CustomFileReadImp;
import core.basesyntax.imp.FruitParseDtoParseImp;
import core.basesyntax.service.CustomFileReader;
import core.basesyntax.service.FruitParse;
import java.util.List;
import org.junit.Test;

public class CustomWriterFileImpTest {
    @Test
    public void checkWriteFile_ok() {
        CustomWriterFile writer = new CustomWriterFileImp();
        CustomFileReader reader = new CustomFileReadImp();
        FruitParse parseImp = new FruitParseDtoParseImp();

        List<String> list = reader.readFromFile("src/test/write.csv");
        parseImp.parse(list);
        writer.writeFile("right.fileWrite.csv", "Hello mates!".trim());
    }
}
