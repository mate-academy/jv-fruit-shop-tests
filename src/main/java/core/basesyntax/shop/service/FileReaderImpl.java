package core.basesyntax.shop.service;

import core.basesyntax.validators.ValidatorForFile;
import core.basesyntax.validators.ValidatorForFileImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderImpl implements FileReader {
    public static final String FILE_DIR = "src/main/resources/";
    private ValidatorForFile validator = new ValidatorForFileImpl();

    public String[] read(String filename) throws IOException {
        String file = FILE_DIR + filename + ".vcs";
        if (validator.test(file)) {
            return Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8)
                    .stream()
                    .toArray(String[]::new);
        }
        return new String[]{};
    }
}
