package com.example.burgerbuilder.repository;

import com.example.burgerbuilder.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, JpaSpecificationExecutor<CustomerOrder> {

    List<CustomerOrder> findByCustomer_Id(Long id);

    List<CustomerOrder> findByCustomer_IdIsIn(List<Long> ids);

}