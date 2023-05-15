package com.epam.messaging;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerId;
    private String itemName;
    private int itemCount;
}