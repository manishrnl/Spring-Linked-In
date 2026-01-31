package com.example.notification_service.clients;

import com.example.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connections")
public interface ConnectionClient {


    @GetMapping("/core/deg-1")
    List<PersonDto> get1DegreeConnections(@RequestHeader("X-User-Id") Long userId);


}
