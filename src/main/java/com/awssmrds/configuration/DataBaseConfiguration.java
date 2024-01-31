package com.awssmrds.configuration;

import com.awssmrds.payload.DbCredentialsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Autowired
    private SecretsManagerClient secretsManagerClient;

    @Bean
    public DataSource dataSource() throws JsonProcessingException {
        DbCredentialsDto postgresCredentials = getPostgresCredentials();
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://"+postgresCredentials.getHost()+":"+postgresCredentials.getPort()+"/"+postgresCredentials.getDbname())
                .username(postgresCredentials.getUsername())
                .password(postgresCredentials.getPassword())
                .build();
    }

    public DbCredentialsDto getPostgresCredentials() throws JsonProcessingException {
        GetSecretValueRequest getSecretValue = GetSecretValueRequest.builder()
                .secretId("database-credentials")
                .build();

        GetSecretValueResponse secretValue = secretsManagerClient.getSecretValue(getSecretValue);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(secretValue.secretString(), DbCredentialsDto.class);
    }
}
