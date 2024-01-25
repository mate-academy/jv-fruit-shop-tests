package core.basesyntax.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FruitDaoImpl implements FruitDao {
    private static final String FILE_NAME = "src/main/java/core/basesyntax/db/database.csv";

    @Override
    public List<String> getCsv() {
        try {
            return Files.readAllLines(Path.of(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + FILE_NAME, e);
        }
    }
}
