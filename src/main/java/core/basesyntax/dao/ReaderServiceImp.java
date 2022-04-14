package core.basesyntax.dao;

import core.basesyntax.validator.Validator;
import core.basesyntax.validator.ValidatorImp;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderServiceImp implements ReaderService {
    private final Validator validator = new ValidatorImp();

    @Override
    public List<String> readFromFile(String filePath) {
        List<String> activitiesLines;
        if (filePath == null) {
            throw new RuntimeException("filePath is null!!");
        }
        try {
            activitiesLines = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from the file!", e);
        }
        return activitiesLines.stream()
                .filter(validator::validationReportLine)
                .collect(Collectors.toList());
    }
}
