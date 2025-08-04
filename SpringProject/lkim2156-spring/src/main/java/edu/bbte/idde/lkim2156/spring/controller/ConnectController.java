package edu.bbte.idde.lkim2156.spring.controller;

import edu.bbte.idde.lkim2156.spring.dao.NotFoundException;
import edu.bbte.idde.lkim2156.spring.dto.incoming.WebshopInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.WebshopOutDto;
import edu.bbte.idde.lkim2156.spring.exception.WeshopNotFoundException;
import edu.bbte.idde.lkim2156.spring.mapper.WebshopMapper;
import edu.bbte.idde.lkim2156.spring.service.StoreService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores/{id}/webshops")
public class ConnectController {


    @Autowired
    private WebshopMapper webshopMapper;

    @Autowired
    private StoreService storeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectController.class);

    @GetMapping()
    public ResponseEntity<Page<WebshopOutDto>> getWebshop(@PathVariable Integer id,
                                                          @RequestParam(defaultValue = "0") int pageNr,
                                                          @RequestParam(defaultValue = "10") int pageSize,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "desc") String sortType) {
        Sort.Direction direction = Sort.Direction.fromString(sortType);
        Sort orders = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNr, pageSize, orders);

        Page<WebshopOutDto> response = storeService.getWebshopsByStoreId(id, pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(response.getTotalElements()))
                .body(response);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WebshopOutDto> createWebshop(@PathVariable Integer id,
                                                       @RequestBody @Valid WebshopInDto webshopRequestDto) {
        try {
            return new ResponseEntity<>(storeService.createWebshopByStore(id, webshopRequestDto), HttpStatus.CREATED);
        } catch (WeshopNotFoundException | NotFoundException e) {
            return new ResponseEntity("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{webshopId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteWebshop(@PathVariable Integer id, @PathVariable Integer webshopId) {

        try {
            storeService.deleteWebshopByStore(id, webshopId);
            return ResponseEntity.ok("The resource was successfully deleted.");
        } catch (WeshopNotFoundException | NotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
