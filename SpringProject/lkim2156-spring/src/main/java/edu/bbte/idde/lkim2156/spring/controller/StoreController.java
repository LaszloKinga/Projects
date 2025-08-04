package edu.bbte.idde.lkim2156.spring.controller;

import edu.bbte.idde.lkim2156.spring.dto.incoming.StoreInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.StoreOutDto;
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
@RequestMapping("/stores")
public class StoreController {


    @Autowired
    private StoreService serviceStore;

    private static final Logger LOG = LoggerFactory.getLogger(StoreController.class);

    @GetMapping
    public ResponseEntity<Page<StoreOutDto>> getStore(@RequestParam(defaultValue = "0") int pageNr,
                                                      @RequestParam(defaultValue = "10") int pageSize,
                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                      @RequestParam(defaultValue = "desc") String sortType) {


        Sort.Direction direction = Sort.Direction.fromString(sortType);
        Sort orders = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNr, pageSize, orders);

        Page<StoreOutDto> response = serviceStore.getStore(pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(response.getTotalElements()))
                .body(response);

    }


    @GetMapping("/{id}")
    public ResponseEntity<StoreOutDto> getStoreById(@PathVariable Integer id) {

        return new ResponseEntity<>(serviceStore.getStoreById(id), HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StoreOutDto> createStore(@RequestBody @Valid StoreInDto webshopRequestDTO) {

        return new ResponseEntity<>(serviceStore.createStore(webshopRequestDTO), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreOutDto> updateStore(@PathVariable Integer id,
                                                   @RequestBody StoreInDto storeRequestDTO) {
        return new ResponseEntity<>(serviceStore.updateStore(id, storeRequestDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable Integer id) {

        serviceStore.deleteStore(id);
    }

}
