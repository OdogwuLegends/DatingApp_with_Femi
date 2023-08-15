package com.legends.promiscuous.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserRequest {
    private long id;
    private int page;
    private int pageSize;
}
