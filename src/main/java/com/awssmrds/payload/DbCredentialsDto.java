package com.awssmrds.payload;

import lombok.Data;

@Data
public class DbCredentialsDto {

    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbname;
}
