package com.example.server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@ToString
@Jacksonized
public class RequestDTO {
    private final RequestType requestType;
    private final Long id;
}
