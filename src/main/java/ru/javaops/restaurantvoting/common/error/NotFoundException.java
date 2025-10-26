package ru.javaops.restaurantvoting.common.error;

import static ru.javaops.restaurantvoting.common.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {

    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}
