package io.swagger.api;

import io.swagger.model.Error;
import io.swagger.model.Pets;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-01T15:30:38.891Z[GMT]")
@Controller
public class PetsApiController implements PetsApi {

    private static final Logger log = LoggerFactory.getLogger(PetsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PetsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createPets() {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Pets> listPets(@ApiParam(value = "How many items to return at one time (max 100)") @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Pets>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Pets> showPetById(@ApiParam(value = "The id of the pet to retrieve",required=true) @PathVariable("petId") String petId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Pets>(HttpStatus.NOT_IMPLEMENTED);
    }

}
