package com.example.server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@ToString
@Jacksonized
public class ChangeBalanceDTO implements Serializable {
    private final Long id;

    private final Long amount;
}
