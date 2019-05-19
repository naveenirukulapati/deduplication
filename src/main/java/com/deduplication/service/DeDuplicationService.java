package com.deduplication.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.deduplication.dto.Cluster;
import com.deduplication.dto.Customer;
import com.deduplication.dto.Return;
import com.deduplication.repository.ClusterRepository;
import com.deduplication.repository.CustomerRepository;

@Service
public class DeDuplicationService {

	@Autowired CustomerRepository customerRepository;
	@Autowired ClusterRepository clusterRepository;
	@Autowired ResourceLoader resourceLoader;
	
	private final String COMMA_DELIMITER = ",";
	
	/**
	 * Service method to ingest the file and find the possible duplicates
	 * @param fileName This method assumes file exists at the classpath. If file is not found then throws FileNotFoundException.
	 * @exceptin FileNotFoundException, IOException
	 * @return Object On success an Result object with possible duplicates and unique rows.
	 */
	public Object deDuplicateData(String fileName) throws FileNotFoundException, IOException {
		
		//Algorithm
		//Create or truncate table
		//Open file
		//For each record in file check if any records matching with first name or last name or email or phone number
		//If any records match then apply Metaphone and identify if they are dups or not
		//Update table with duplicates
		
		
		//delete tables for every run
		customerRepository.deleteAll();
		clusterRepository.deleteAll();
		
		//read file line by line
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceLoader.getResource("classpath:"+fileName).getInputStream()));
		String line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			//Split comma separated columns
			String[] columns = StringUtils.splitPreserveAllTokens(line, COMMA_DELIMITER);
			Customer customer = convertArrayToCustomerObject(columns);
			//Find if any records already eist with matching first or last name or email
			customerRepository.save(findPossibleDuplicates(customer));
		}
		
		//After processing the file. get all dups and unique records and return to the user.
		return getResults();
	}
	
	/**
	 * This method identifies if current customer is a duplicate or not.
	 * @param customer Current customer record object.
	 * @return Customer
	 */
	public Customer findPossibleDuplicates(Customer customer) {
		List<Customer> possibleDuplicateCustomers = customerRepository.findAllDuplicates(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone());
		//If any dupes found then apply Metaphone algorithm for all the fields. If first and last names match and any 3 or more fields match consider that as a duplicate.
		if(possibleDuplicateCustomers != null && !possibleDuplicateCustomers.isEmpty()) {
			//Checks with possible duplicates and if duplicate found based on first name, last name or email then assign both to the same group and save customer.
			for(Customer possibleDuplicateCustomer : possibleDuplicateCustomers) {
				if(determineIf2CustomersAreDups(customer, possibleDuplicateCustomer)) {
					customer.setGroupId(possibleDuplicateCustomer.getGroupId());
					break;
				}
			}
		}
		//If rcords are not matching then save current record as new group.
		if(customer.getGroupId() == null) {
			//no Dups found. create new group and assig that id to new record and save it.
			Cluster cluster = clusterRepository.save(new Cluster());
			customer.setGroupId(cluster.getId());
		}
		return customer;
	}
	
	/**
	 * This method gets all the possible duplicates and unique records.
	 * @return Return
	 */
	public Return getResults() {
		Return returnObject = new Return();
		returnObject.setPossibleDuplicateCustomers(customerRepository.findAllDuplicateCustomers());
		returnObject.setUniqueCustomers(customerRepository.findAllUniqueCustomers());
		return returnObject;
	}
	
	/**
	 * This method accepts 2 customer objects and identifies if they are possible duplicates or not.
	 * Duplicates are identified as if first and last names are matching and 3 or more fields are matching.
	 * @param customer1
	 * @param customer2
	 * @return boolean
	 */
	public boolean determineIf2CustomersAreDups(Customer customer1, Customer customer2) {
		//If first and last names are matching and 3 more fields match then consider as duplicate
		int numberOfFieldsMatch = 0;
		if(isSame(customer1.getFirstName(), customer2.getFirstName())
			&& isSame(customer1.getLastName(), customer2.getLastName())) {
			if(isSame(customer1.getEmail(), customer2.getEmail())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getCompany(), customer2.getCompany())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getAddress1(), customer2.getAddress1())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getAddress2(), customer2.getAddress2())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getZip(), customer2.getZip())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getCity(), customer2.getCity())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getStateLong(), customer2.getStateLong())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getState(), customer2.getState())) {
				numberOfFieldsMatch++;
			}
			if(isSame(customer1.getPhone(), customer2.getPhone())) {
				numberOfFieldsMatch++;
			}
			if(numberOfFieldsMatch >= 3) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method accepts 2 strings and uses Metaphone algorithm to determine if they are possible duplicates or not.
	 * @param firstString
	 * @param secondString
	 * @return
	 */
	public boolean isSame(String firstString, String secondString) {
		Metaphone metaphone = new Metaphone();
		metaphone.setMaxCodeLen(100);
		return metaphone.isMetaphoneEqual(firstString, secondString);
	}
	
	/**
	 * Converts a string array in to Customer object. 
	 * @param columns
	 * @return Customer
	 */
	private Customer convertArrayToCustomerObject(String[] columns) {
		if(columns.length < 12) {
			System.out.println("");
			return null;
		}
		Customer customer = new Customer();
		customer.setId(Long.parseLong(columns[0]));
		customer.setFirstName(columns[1]);
		customer.setLastName(columns[2]);
		customer.setCompany(columns[3]);
		customer.setEmail(columns[4]);
		customer.setAddress1(columns[5]);
		customer.setAddress2(columns[6]);
		customer.setZip(columns[7]);
		customer.setCity(columns[8]);
		customer.setStateLong(columns[9]);
		customer.setState(columns[10]);
		customer.setPhone(columns[11]);
		return customer;
	}
}
