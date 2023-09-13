package com.luv2code.ecommerce.dao;

import com.luv2code.ecommerce.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

//@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "states", path = "states")

public interface StateRepository extends JpaRepository<State, Integer> {
    // return the list of states that belong to a given country (by using country code)
    // available at: http://localhost:8080/api/states/search/findByCountryCode{?code}
    List<State> findByCountryCode(@Param("code") String code);
}
