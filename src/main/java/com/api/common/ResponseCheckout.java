package com.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCheckout {

    OK("00", "Confirm Success" ),
    EXISTED("02","Order already confirmed"),
    NOTOK("01","Not success"),
    INVALID("97","Invalid Checksum");

    private final String responseCode;
    private final String message;
}
