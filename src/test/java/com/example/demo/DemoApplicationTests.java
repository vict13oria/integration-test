package com.example.demo;


import com.example.demo.controller.VehicleController;
import com.example.demo.domain.Vehicle;
import com.example.demo.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
class DemoApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    VehicleService vehicleService;

    @Test
    void getAllVehicles() throws Exception {
        List<Vehicle> list = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle("AD23E5R98EFT3SL00", "Ford", "Fiesta", 2016, false);
        Vehicle vehicle2 = new Vehicle("O90DEPADE564W4W83", "Volkswagen", "Jetta", 2016, false);

        list.add(vehicle1);
        list.add(vehicle2);

        Mockito.when(vehicleService.getAllVehicles()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/demo/vehicles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].vin", is("AD23E5R98EFT3SL00")))
                .andExpect(jsonPath("$[0].make", is("Ford")))
                .andExpect(jsonPath("$[1].vin", is("O90DEPADE564W4W83")))
                .andExpect(jsonPath("$[1].make", is("Volkswagen")));

    }

    @Test
    void postVehicles() throws Exception {
        Vehicle vehicle = new Vehicle("AD23E5R98EFT3SL00", "Ford", "Fiesta", 2016, false);

        Mockito.when(vehicleService.createVehicle(Mockito.any(Vehicle.class))).thenReturn(vehicle);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/demo/create/vehicle")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(vehicle));

        mockMvc.perform(builder).andExpect(status().isCreated())
                .andExpect(jsonPath("$.vin", is("AD23E5R98EFT3SL00")))
                .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(vehicle)));
    }

}
