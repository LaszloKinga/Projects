package edu.bbte.idde.lkim2156.spring.controller;

import edu.bbte.idde.lkim2156.spring.dto.incoming.WebshopInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.WebshopOutDto;
import edu.bbte.idde.lkim2156.spring.pojo.WebshopFilter;
import edu.bbte.idde.lkim2156.spring.service.ServiceWebshop;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/webshops")
public class WebshopController {

    @Autowired
    private ServiceWebshop serviceWebshop;

    private static final Logger LOG = LoggerFactory.getLogger(WebshopController.class);

    @GetMapping
    public ResponseEntity<Page<WebshopOutDto>> getWebshops(@RequestParam(required = false) String paymentMethod,
                                                           @RequestParam(required = false) Integer store,
                                                           @RequestParam(required = false) Double minTotalAmount,
                                                           @RequestParam(required = false) Double maxTotalAmount,
                                                           @RequestParam(required = false) Boolean shipped,
                                                           @RequestParam(required = false) Integer storeId,
                                                           @RequestParam(defaultValue = "0") int pageNr,
                                                           @RequestParam(defaultValue = "10") int pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "desc") String sortType) {


        Sort.Direction direction = Sort.Direction.fromString(sortType);
        Sort orders = Sort.by(direction, sortBy);

        WebshopFilter filter = new WebshopFilter(
                minTotalAmount,
                maxTotalAmount,
                paymentMethod,
                shipped,
                storeId);


        LOG.info("Filter received: {}", filter);
        Pageable pageable = PageRequest.of(pageNr, pageSize, orders);

        Page<WebshopOutDto> response = serviceWebshop.getWebshops(filter, pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(response.getTotalElements()))
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<WebshopOutDto> getWebshopById(@PathVariable Integer id) {

        return new ResponseEntity<>(serviceWebshop.getWebshopById(id), HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WebshopOutDto> createWebshop(@RequestBody @Valid WebshopInDto webshopRequestDTO) {

        return new ResponseEntity<>(serviceWebshop.createWebshop(webshopRequestDTO), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<WebshopOutDto> updateWebshop(@PathVariable Integer id,
                                                       @RequestBody WebshopInDto webshopRequestDTO) {
        return new ResponseEntity<>(serviceWebshop.updateWebshop(id, webshopRequestDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWebshop(@PathVariable Integer id) {
        log.info("Controllerbol id: {}", id);
        serviceWebshop.deleteWebshop(id);
    }


}

