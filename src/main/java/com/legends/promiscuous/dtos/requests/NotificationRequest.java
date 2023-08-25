package com.legends.promiscuous.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private Long sender_id;

    @JsonProperty("to")
    private Long recipients_id;

    @JsonProperty("htmlContent")
    private String subject;
}
