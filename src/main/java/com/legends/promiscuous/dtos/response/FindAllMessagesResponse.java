package com.legends.promiscuous.dtos.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindAllMessagesResponse {
    private List<String> message;
}
