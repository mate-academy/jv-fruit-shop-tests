package core.basesyntax.fao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCreatorImpl implements FileCreator {
    @Override
    public void createInputFile(String fileName) {
        String inputContent = """
                type,fruit,quantity
                b,banana,20
                b,apple,100
                s,banana,100
                p,banana,13
                r,apple,10
                p,apple,20
                p,banana,5
                s,banana,50                          
                """;
        try {
            Files.write(Paths.get(fileName), inputContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unable to create input file: reportToRead.csv", e);
        }
    }
}
