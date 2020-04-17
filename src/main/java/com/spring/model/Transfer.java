package com.spring.model;


import lombok.Data;

import java.math.BigInteger;

@Data
public class Transfer {
    BigInteger id;

    BigInteger time;

    String status;

    String msg;

}
