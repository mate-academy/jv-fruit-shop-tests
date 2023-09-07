package core.basesyntax.service.impl;

import core.basesyntax.service.PathValidator;
import java.io.File;

public class PathValidatorImpl implements PathValidator {

    public PathValidatorImpl() {
    }

    @Override
    public boolean filePathValidator(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }
}
