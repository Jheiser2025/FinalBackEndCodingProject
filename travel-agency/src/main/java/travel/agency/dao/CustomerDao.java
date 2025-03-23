package travel.agency.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import travel.agency.entity.Customer;


public interface CustomerDao extends JpaRepository<Customer, Long>{

}

