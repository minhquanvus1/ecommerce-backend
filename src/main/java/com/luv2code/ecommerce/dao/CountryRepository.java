package com.luv2code.ecommerce.dao;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

//@CrossOrigin("http://localhost:4200") no need, because we've set CORS mapping in MyDataRestConfig file
@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
