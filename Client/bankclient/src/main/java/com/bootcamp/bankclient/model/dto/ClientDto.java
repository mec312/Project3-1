package com.bootcamp.bankclient.model.dto;

import com.bootcamp.bankclient.model.entities.ClientType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {

    private String id;
    private String name;

    private String clientIdType;

    private String clientIdNumber;


    private String email;
    private String phone;
    private String address;

    private ClientType clientType;
}
