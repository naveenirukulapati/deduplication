package com.deduplication.dto;

import java.util.List;

public class Return {
	
	private List<Customer> possibleDuplicateCustomers;
	private List<Customer> uniqueCustomers;
	
	public List<Customer> getPossibleDuplicateCustomers() {
		return possibleDuplicateCustomers;
	}
	public void setPossibleDuplicateCustomers(List<Customer> possibleDuplicateCustomers) {
		this.possibleDuplicateCustomers = possibleDuplicateCustomers;
	}
	public List<Customer> getUniqueCustomers() {
		return uniqueCustomers;
	}
	public void setUniqueCustomers(List<Customer> uniqueCustomers) {
		this.uniqueCustomers = uniqueCustomers;
	}

}
