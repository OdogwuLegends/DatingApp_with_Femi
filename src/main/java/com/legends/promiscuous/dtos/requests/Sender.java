package com.legends.promiscuous.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sender {
    private String name;
    @NonNull
    private String email;
}
