package io.swagger.api;

import io.swagger.model.Pet;
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
    public void createPetTest() throws Exception {
        Pet body = new Pet();
        ResponseEntity<Void> responseEntity = api.createPet(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getPetByIdTest() throws Exception {
        Long petId = 789L;
        ResponseEntity<Pet> responseEntity = api.getPetById(petId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void listPetsTest() throws Exception {
        ResponseEntity<Pets> responseEntity = api.listPets();
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
