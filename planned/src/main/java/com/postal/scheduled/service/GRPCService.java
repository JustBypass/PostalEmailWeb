package com.postal.scheduled.service;

import com.mail.global.clients.online.ClientDTO;
import com.postal.scheduled.utils.Property;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GRPCService {
    @Autowired
    Property property;

    public ClientDTO[] getClientsByGRPC(){
        ManagedChannel channel = ManagedChannelBuilder.forTarget(property.getGrpcServiceHost())
                .usePlaintext().build();

        return null;
    }
}
