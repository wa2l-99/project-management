package com.pmt.project_management.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;


public enum BusinessErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "Pas de code"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Le mot de passe actuel est incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "Le nouveau mot de passe ne correspond pas"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Identifiant et/ou mot de passe incorrect"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code,HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;

    }
}
