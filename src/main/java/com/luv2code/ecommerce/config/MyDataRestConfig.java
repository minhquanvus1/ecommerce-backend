package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration

public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigin;

    // use constructor injection, to inject property of type "EntityManager" to this class
    private EntityManager entityManager;
    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        HttpMethod[] theUnsupportedActions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH};


        // disable Http method for Product Category: POST, PUT, DELETE --> only GET is allowed --> make it READ-ONLY
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(Country.class, config, theUnsupportedActions);
        disableHttpMethods(State.class, config, theUnsupportedActions);
        disableHttpMethods(Order.class, config, theUnsupportedActions);


        // call internal helper method to help us expose the id of ProductCategory
        exposeIds(config);

        // configure CORS mapping for Spring Data Rest
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigin);
    }

    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // expose "id" field, for all entity classes

        // get a set of all entity types from EntityManager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // create an empty list of type Class (to store entity class)
        List<Class> entitClasses = new ArrayList<>();

            // get the entity class (of type Class) for each entity type
            for(EntityType tempEntityType: entities) {
                entitClasses.add(tempEntityType.getJavaType());
            }

            // create a new array of type Class, and assign the List of entityClasses to it --> we have an array of entity classes
        Class[] domainTypes = entitClasses.toArray(new Class[0]);

        // expose the ids for the array of entity classes
            config.exposeIdsFor(domainTypes);
    }
}

