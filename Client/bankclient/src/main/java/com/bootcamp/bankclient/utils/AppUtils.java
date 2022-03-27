package com.bootcamp.bankclient.utils;

import com.bootcamp.bankclient.model.entities.Client;
import com.bootcamp.bankclient.model.dto.ClientDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ClientDto entityToDto(Client client){
        ClientDto clientDto=new ClientDto();
        BeanUtils.copyProperties(client,clientDto);
        return clientDto;
    }

    public static Client dtoToEntity(ClientDto clientDto){
        Client client=new Client();
        BeanUtils.copyProperties(clientDto,client);
        return client;
    }
}
