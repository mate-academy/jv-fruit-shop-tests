package core.basesyntax.service.file;

import core.basesyntax.service.file.validator.ReadValidator;
import core.basesyntax.service.file.validator.WriteValidator;
import java.util.List;

public interface FileOperations extends ReadValidator, WriteValidator {
    List<String> read(String fileName);

    boolean write(String dataToWrite, String path);
}

