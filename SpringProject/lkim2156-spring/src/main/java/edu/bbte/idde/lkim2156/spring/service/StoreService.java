package edu.bbte.idde.lkim2156.spring.service;

import edu.bbte.idde.lkim2156.spring.dao.NotFoundException;
import edu.bbte.idde.lkim2156.spring.dao.StoreDao;
import edu.bbte.idde.lkim2156.spring.dao.WebshopDao;
import edu.bbte.idde.lkim2156.spring.dto.incoming.StoreInDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.WebshopInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.StoreOutDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.WebshopOutDto;
import edu.bbte.idde.lkim2156.spring.exception.WeshopNotFoundException;
import edu.bbte.idde.lkim2156.spring.mapper.StoreMapper;
import edu.bbte.idde.lkim2156.spring.mapper.WebshopMapper;
import edu.bbte.idde.lkim2156.spring.model.Store;
import edu.bbte.idde.lkim2156.spring.model.Webshop;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private WebshopDao webshopDao;

    @Autowired
    private WebshopMapper webshopMapper;

    @Autowired
    private StoreMapper storeMapper;

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);


    public Page<StoreOutDto> getStore(Pageable pageable) {
        LOG.info("Fetching all stores with pagination and sorting.");
        return storeDao.findAll(pageable)
                .map(storeMapper::mapEntityToResponseDto);

    }


    public StoreOutDto getStoreById(Integer id) {
        Store storeTmp = storeDao.findById(id).orElseThrow(() -> new NotFoundException("Store not found"));

        StoreOutDto store = storeMapper.mapEntityToResponseDto(storeTmp);
        if (store == null) {
            throw new NotFoundException("Error");
        } else {
            return store;
        }
    }


    public StoreOutDto createStore(StoreInDto storeRequestDTO) {

        Store storeEntity = storeMapper.mapRequestDtoToEntity(storeRequestDTO);
        Store createdStore = storeDao.save(storeEntity);
        return storeMapper.mapEntityToResponseDto(createdStore);

    }


    public StoreOutDto updateStore(Integer id,
                                   StoreInDto storeRequestDTO) {
        Store updateM = storeMapper.mapRequestDtoToEntity(storeRequestDTO);
        updateM.setId(id);
        Store updatedStore = storeDao.saveAndFlush(updateM);

        return storeMapper.mapEntityToResponseDto(updatedStore);

    }


    public void deleteStore(Integer id) {
        storeDao.deleteById(id);
    }

    public Page<WebshopOutDto> getWebshopsByStoreId(Integer id, Pageable pageable) {

        Store store = storeDao.findById(id)
                .orElseThrow(() -> new WeshopNotFoundException("Store not found with id: " + id));


        Page<Webshop> webshops = webshopDao.findByStoreId(store, pageable);


        return webshops.map(webshopMapper::mapEntityToResponseDto);
    }

    @CacheEvict(value = "webshops", allEntries = true)
    public WebshopOutDto createWebshopByStore(Integer id, WebshopInDto webshopRequestDto) {
        Store store = storeDao.findById(id)
                .orElseThrow(() -> new WeshopNotFoundException("Store not found with id: " + id));

        Webshop webshopEntity = webshopMapper.mapRequestDtoToEntity(webshopRequestDto);
        webshopEntity.setStoreId(store);


        Webshop createdWebshop = webshopDao.save(webshopEntity);

        return webshopMapper.mapEntityToResponseDto(createdWebshop);
    }


    @CacheEvict(value = "webshops", allEntries = true)
    public void deleteWebshopByStore(Integer id, Integer webshopId) {
        Store store = storeDao.findById(id)
                .orElseThrow(() -> new WeshopNotFoundException("Store not found with id: " + id));

        Webshop webshop = webshopDao.findById(webshopId)
                .orElseThrow(() -> new WeshopNotFoundException("Webshop not found with id: " + webshopId));

        if (!webshop.getStoreId().equals(store)) {

            throw new WeshopNotFoundException("Webshop not found for store with id: " + id);
        }

        webshopDao.clearStoreReference(webshopId);
        webshopDao.deleteWebshopById(webshopId);

    }


}
