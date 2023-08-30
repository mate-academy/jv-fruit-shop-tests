package core.basesyntax.service.impl;

import core.basesyntax.service.PathValidator;

public class PathValidatorImpl implements PathValidator {

    public PathValidatorImpl() {
    }

    @Override
    public boolean filePathValidator(String filePath) {
        java.io.File file = new java.io.File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }
}
