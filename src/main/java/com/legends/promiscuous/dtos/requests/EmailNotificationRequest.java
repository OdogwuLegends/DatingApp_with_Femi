package com.legends.promiscuous.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.legends.promiscuous.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static com.legends.promiscuous.utils.AppUtils.APP_EMAIL;
import static com.legends.promiscuous.utils.AppUtils.APP_NAME;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmailNotificationRequest {
    private final Sender sender = new Sender(APP_NAME,APP_EMAIL);
    //to
    @JsonProperty("to")
    private List<Recipient> recipients;
    //cc
    @JsonProperty("cc")
    private List<String> copiedEmails;

    //htmlContent
    @JsonProperty("htmlContent")
    private String mailContent;

    private String textContent;

    private String subject;
}
