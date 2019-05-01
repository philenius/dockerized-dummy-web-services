package io.swagger.api;

import io.swagger.model.Error;
import io.swagger.model.Pets;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetsApiControllerIntegrationTest {

    @Autowired
    private PetsApi api;

    @Test
    public void createPetsTest() throws Exception {
        ResponseEntity<Void> responseEntity = api.createPets();
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void listPetsTest() throws Exception {
        Integer limit = 56;
        ResponseEntity<Pets> responseEntity = api.listPets(limit);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void showPetByIdTest() throws Exception {
        String petId = "petId_example";
        ResponseEntity<Pets> responseEntity = api.showPetById(petId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
