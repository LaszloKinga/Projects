package edu.bbte.idde.lkim2156.spring.service;

import edu.bbte.idde.lkim2156.spring.controller.WebshopController;
import edu.bbte.idde.lkim2156.spring.dao.NotFoundException;
import edu.bbte.idde.lkim2156.spring.dao.StoreDao;
import edu.bbte.idde.lkim2156.spring.dao.WebshopDao;
import edu.bbte.idde.lkim2156.spring.dto.incoming.WebshopInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.WebshopOutDto;
import edu.bbte.idde.lkim2156.spring.exception.WeshopNotFoundException;
import edu.bbte.idde.lkim2156.spring.mapper.WebshopMapper;
import edu.bbte.idde.lkim2156.spring.model.Webshop;
import edu.bbte.idde.lkim2156.spring.pojo.WebshopFilter;
import edu.bbte.idde.lkim2156.spring.specification.WebshopSpecification;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceWebshop {

    @Autowired
    private WebshopDao webshopDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private WebshopMapper webshopMapper;

    private static final Logger LOG = LoggerFactory.getLogger(WebshopController.class);

    @Cacheable(value = "webshops", key = "'getWebshops-' + "
            + "(#filter.minTotalAmount == null ? 'NULL' : #filter.minTotalAmount) + '-' + "
            + "(#filter.maxTotalAmount == null ? 'NULL' : #filter.maxTotalAmount) + '-' + "
            + "(#filter.paymentMethod == null ? 'NULL' : #filter.paymentMethod) + '-' + "
            + "(#filter.shipped == null ? 'NULL' : #filter.shipped) + '-' + "
            + "(#filter.storeId == null ? 'NULL' : #filter.storeId) + '-' + "
            + "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<WebshopOutDto> getWebshops(WebshopFilter filter, Pageable pageable) {
        if (filter.getStoreId() != null) {
            storeDao.findById(filter.getStoreId())
                    .orElseThrow(() ->
                            new WeshopNotFoundException("Store not found with id: " + filter.getStoreId()));
        }


        Specification<Webshop> spec = new WebshopSpecification(filter);

        Page<Webshop> resultPage = webshopDao.findAll(spec, pageable);

        return resultPage.map(webshopMapper::mapEntityToResponseDto);
    }


    @Cacheable(value = "webshops", key = "#id")
    public WebshopOutDto getWebshopById(Integer id) {
        Webshop webshopTemp = webshopDao.findById(id).orElseThrow(() -> new NotFoundException("Webshop not found"));
        WebshopOutDto webshop = webshopMapper.mapEntityToResponseDto(webshopTemp);
        if (webshop == null) {
            throw new WeshopNotFoundException("Error");
        } else {
            return webshop;
        }
    }


    @CacheEvict(value = "webshops", allEntries = true)
    public WebshopOutDto createWebshop(WebshopInDto webshopRequestDTO) {

        Webshop webshopEntity = webshopMapper.mapRequestDtoToEntity(webshopRequestDTO);
        Webshop createdWebshop = webshopDao.save(webshopEntity);
        return webshopMapper.mapEntityToResponseDto(createdWebshop);

    }

    @CacheEvict(value = "webshops", allEntries = true)
    public WebshopOutDto updateWebshop(Integer id,
                                       WebshopInDto webshopRequestDTO) {
        Webshop updateM = webshopMapper.mapRequestDtoToEntity(webshopRequestDTO);
        updateM.setId(id);
        Webshop updatedWebshop = webshopDao.saveAndFlush(updateM);

        return webshopMapper.mapEntityToResponseDto(updatedWebshop);

    }


    @CacheEvict(value = "webshops", allEntries = true)
    public void deleteWebshop(Integer id) {
        log.info("id servicebol: {}", id);
        webshopDao.clearStoreReference(id);
        webshopDao.deleteById(id);

    }

}
