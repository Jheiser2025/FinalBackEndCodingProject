package travel.agency.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import travel.agency.entity.Agency;
import travel.agency.entity.Customer;
import travel.agency.entity.Locations;

@Data
@NoArgsConstructor

public class TravelAgencyData {

	private Long agencyId;
	private  String agencyName;
	private  String agencyEmail;
	private  String agencyPhone;

	private Set<CustomerData> customers = new HashSet<>();
	private Set<LocationData> locations = new HashSet<>();

	public TravelAgencyData(Agency agency) {
	agencyId = agency.getAgencyId();
	agencyName = agency.getAgencyName();
	agencyEmail = agency.getAgencyEmail();
	agencyPhone = agency.getAgencyPhone();
	
	for (Customer customer : agency.getCustomers()) {
		customers.add(new CustomerData(customer));
	}
	for (Locations location : agency.getLocations()) {
		locations.add(new LocationData(location));
	}
	}
	@Data
	@NoArgsConstructor
	public static class CustomerData {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
	public CustomerData(Customer customer) {
	customerId = customer.getCustomerId();
	customerFirstName = customer.getCustomerFirstName();
	customerLastName = customer.getCustomerLastName();
	customerEmail = customer.getCustomerEmail();
}
}
	@Data
	@NoArgsConstructor
	public static class LocationData {
	
	private Long locationId;
	private String locationCountry;
	private String locationCity;

	
	public LocationData(Locations locations) {
		locationId = locations.getLocationId();
		locationCountry = locations.getLocationCountry();
		locationCity = locations.getLocationCity();	
}

}
}