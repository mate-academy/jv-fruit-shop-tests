package core.basesyntax.service.file;

import core.basesyntax.model.CsvLineDto;
import java.util.List;

public interface Validator {
    boolean checkFileData(List<CsvLineDto> dataFromFile);
}
