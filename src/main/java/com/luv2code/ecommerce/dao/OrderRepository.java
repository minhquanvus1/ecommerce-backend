package com.luv2code.ecommerce.dao;

import com.luv2code.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin("https://localhost:4200")
@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {

    // return list of Orders by customer's email
    // select * from Orders left join Customer on orders.customer_id = customer.id where customer.email = : email order by orders.date_created desc;
    // available at: http://localhost:8080/api/orders/search/findByCustomerEmailOrderByDateCreatedDesc{?email}
    Page<Order> findByCustomerEmailOrderByDateCreatedDesc(@Param("email") String email, Pageable pageable);
}
