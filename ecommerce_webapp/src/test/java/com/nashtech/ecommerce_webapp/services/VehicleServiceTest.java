package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.models.Vehicle;
import com.nashtech.ecommerce_webapp.repositories.VehicleRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@SpringBootTest
public class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;

    @InjectMocks
    VehicleService vehicleService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetVehicleById(){
        UUID id = UUID.randomUUID();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setName("abc");
        vehicle.setColor("pink");
        vehicle.setDateOfManufacture(1L);
        vehicle.setPrice(123);
        vehicle.setQuantity(123);
        vehicle.setStatus(true);
        when(vehicleRepository.getById(id)).thenReturn(null);

    }

}
