package com.credit.card.dto;

import com.credit.card.bean.ClientType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {

    private String id;
    private String name;

    private String clientNumber;

    private String email;
    private String phone;
    private String address;

    private ClientType clientType;

}
