package com.deduplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deduplication.dto.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "SELECT * FROM Customer c WHERE c.firstName like :firstName or c.lastName like :lastName or c.email like :email or c.phone like :phone", nativeQuery = true)
	public List<Customer> findAllDuplicates(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("phone") String phone);
	
	@Query(value = "SELECT * FROM Customer c where groupId in (Select groupId from customer ca GROUP BY ca.groupId having count(1) > 1) order by groupId", nativeQuery = true)
	public List<Customer> findAllDuplicateCustomers();

	@Query(value = "SELECT * FROM Customer c where groupId in (Select groupId from customer ca GROUP BY ca.groupId having count(1) = 1) order by groupId", nativeQuery = true)
	public List<Customer> findAllUniqueCustomers();

}
