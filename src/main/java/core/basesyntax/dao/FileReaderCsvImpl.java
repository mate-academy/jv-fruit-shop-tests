package core.basesyntax.dao;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.services.ValidatorService;
import core.basesyntax.services.ValidatorServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderCsvImpl implements FileReader {
    private final ValidatorService validatorService = new ValidatorServiceImpl();

    @Override
    public List<String> getData(String fileName) {
        validatorService.nullInputDataValidator(fileName);
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new ValidationException("Can't get data from file");
        }
        validatorService.emptyInputDataValidator(dataFromFile);
        return dataFromFile;
    }
}
