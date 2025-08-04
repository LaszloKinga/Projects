package edu.bbte.idde.lkim2156.spring.specification;

import edu.bbte.idde.lkim2156.spring.model.Webshop;
import edu.bbte.idde.lkim2156.spring.pojo.WebshopFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WebshopSpecification implements Specification<Webshop>, Serializable {
    private static final long serialVersionUID = 1L;

    private final WebshopFilter filter;

    public WebshopSpecification(WebshopFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Webshop> webshopRoot,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();


        if (filter.getMinTotalAmount() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(webshopRoot.get("totalAmount"),
                    filter.getMinTotalAmount()));
        }

        if (filter.getMaxTotalAmount() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(webshopRoot.get("totalAmount"),
                    filter.getMaxTotalAmount()));
        }

        if (filter.getPaymentMethod() != null) {
            predicates.add(criteriaBuilder.equal(webshopRoot.get("paymentMethod"),
                    filter.getPaymentMethod()));
        }


        if (filter.getShipped() != null) {
            predicates.add(criteriaBuilder.equal(webshopRoot.get("isShipped"),
                    filter.getShipped()));
        }

        if (filter.getStoreId() != null) {
            predicates.add(criteriaBuilder.equal(webshopRoot.get("storeId").get("id"),
                    filter.getStoreId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
