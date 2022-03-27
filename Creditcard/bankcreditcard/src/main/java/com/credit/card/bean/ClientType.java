package com.credit.card.bean;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientType {

    @Id
    private String id;
    @Indexed(unique = true)
    private String code;
    @Indexed(unique = true)
    private String name;
}
