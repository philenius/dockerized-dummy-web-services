package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Pet;
import io.swagger.model.Pets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-01T18:35:00.548Z[GMT]")
@Controller
public class PetsApiController implements PetsApi {

    private static final Logger log = LoggerFactory.getLogger(PetsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private Pets pets = new Pets();

    @org.springframework.beans.factory.annotation.Autowired
    public PetsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        initializeStaticPets();
    }

    private void initializeStaticPets() {
        Map<String, String> nameTagMap = new HashMap<>();
        nameTagMap.put("Garfield", "cat");
        nameTagMap.put("Pink Panther", "cat");
        nameTagMap.put("Tom", "cat");
        nameTagMap.put("Hello Kitty", "cat");
        nameTagMap.put("Puss in Boots", "cat");
        nameTagMap.put("Grumpy Cat", "cat");
        nameTagMap.put("Odie", "dog");
        nameTagMap.put("Pluto", "dog");
        nameTagMap.put("Snoopy", "dog");
        nameTagMap.put("Scooby Doo", "dog");

        long l = 0;
        for (Map.Entry<String, String> entry : nameTagMap.entrySet()) {
            Pet p = new Pet();
            p.setId(l++);
            p.setName(entry.getKey());
            p.setTag(entry.getValue());
            pets.add(p);
        }
    }

    public ResponseEntity<Void> createPet(@ApiParam(value = "Pet object that needs to be added to the store", required = true) @Valid @RequestBody Pet body) {
        List<Pet> petsWithSameId = pets.stream().filter(pet -> pet.getId() == body.getId()).collect(Collectors.toList());
        pets.add(body);
        if (petsWithSameId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        pets.remove(petsWithSameId.get(0));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Pet> getPetById(@ApiParam(value = "ID of pet to return", required = true) @PathVariable("petId") Long petId) {
        String accept = request.getHeader("Accept");

        List<Pet> foundPets = pets.stream().filter(pet -> pet.getId() == petId).collect(Collectors.toList());
        if (foundPets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pet>(foundPets.get(0), HttpStatus.OK);
    }

    public ResponseEntity<Pets> listPets() {
        return new ResponseEntity<Pets>(pets, HttpStatus.OK);
    }

}
