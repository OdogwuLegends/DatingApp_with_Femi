package com.legends.promiscuous.dtos.requests;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Recipient {
    private String name;
    @NonNull
    private String email;
}
