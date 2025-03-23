package travel.agency.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.agency.controller.model.TravelAgencyData;
import travel.agency.controller.model.TravelAgencyData.CustomerData;
import travel.agency.controller.model.TravelAgencyData.LocationData;
import travel.agency.dao.CustomerDao;
import travel.agency.dao.LocationsDao;
import travel.agency.dao.TravelAgencyDao;
import travel.agency.entity.Agency;
import travel.agency.entity.Customer;
import travel.agency.entity.Locations;

@Service
public class TravelAgencyService {
	@Autowired
	private TravelAgencyDao travelAgencyDao;

	@Transactional(readOnly = false)
	public TravelAgencyData saveAgency(TravelAgencyData travelAgencyData) {
		Long agencyId = travelAgencyData.getAgencyId();
		Agency agency = findOrCreateAgency(agencyId);

		copyAgencyFields(agency, travelAgencyData);
		return new TravelAgencyData(travelAgencyDao.save(agency));

	}

	private void copyAgencyFields(Agency agency, TravelAgencyData travelAgencyData) {
		agency.setAgencyId(travelAgencyData.getAgencyId());
		agency.setAgencyName(travelAgencyData.getAgencyName());
		agency.setAgencyEmail(travelAgencyData.getAgencyEmail());
		agency.setAgencyPhone(travelAgencyData.getAgencyPhone());

	}

	private Agency findOrCreateAgency(Long agencyId) {
		if (Objects.isNull(agencyId)) {
			return new Agency();
		} else {
			return findAgencyById(agencyId);
		}

	}

	private Agency findAgencyById(Long agencyId) {
		return travelAgencyDao.findById(agencyId).orElseThrow(
				() -> new NoSuchElementException("Travel Agency with ID = " + agencyId + " was not found."));
	}

	@Transactional(readOnly = true)

	public  List<TravelAgencyData> retrieveAllAgencies() {
		List<Agency> agencies = travelAgencyDao.findAll();
		List<TravelAgencyData> result = new LinkedList<>();
		for (Agency agency : agencies) {
			TravelAgencyData psd = new TravelAgencyData(agency);
			psd.getCustomers().clear();
			psd.getLocations().clear();

			result.add(psd);
		}
		return result;
	}

	public TravelAgencyData retrieveAgencyById(Long agencyId) {
		return new TravelAgencyData(findAgencyById(agencyId));
	}

	@Transactional(readOnly = false)
	public void deleteAgencyId(Long agencyId) {
		Agency agency = findAgencyById(agencyId);
		travelAgencyDao.delete(agency);
	}

	@Autowired
	private LocationsDao locationsDao;

	@Transactional(readOnly = false)
	public LocationData saveLocations(Long agencyId, LocationData locationData) {
		Agency agency = findAgencyById(agencyId);
		Long locationId = locationData.getLocationId();
		Locations location = findOrCreateLocationById(agencyId, locationId);

		copyLocationsFields(location, locationData);
		location.setAgency(agency);
		agency.getLocations().add(location);
		Locations dbLocations = locationsDao.save(location);
		return new LocationData(dbLocations);

	}

	private Locations findLocationsById(Long agencyId, Long locationId) {
		Locations locations = locationsDao.findById(agencyId)
				.orElseThrow(() -> new NoSuchElementException("Location not found"));
		if (locations.getAgency().getAgencyId() != agencyId) {
			throw new IllegalArgumentException("Location does not belong to the given travel agency");
		}
		return locations;
	}

	private Locations findOrCreateLocationById(Long agencyId, Long locationsId) {
		if (Objects.isNull(locationsId)) {
			return new Locations();
		}
		return findLocationsById(agencyId, locationsId);
	}

	private void copyLocationsFields(Locations locations, LocationData locationData) {
		locations.setLocationId(locationData.getLocationId());
		locations.setLocationCity(locationData.getLocationCity());
		locations.setLocationCountry(locationData.getLocationCountry());
	}

	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = false)
	private void copyCustomerFields(Customer customer, CustomerData customerData) {
		customer.setCustomerEmail(customerData.getCustomerEmail());
		customer.setCustomerFirstName(customerData.getCustomerFirstName());
		customer.setCustomerId(customerData.getCustomerId());
		customer.setCustomerLastName(customerData.getCustomerLastName());
	}

	private Customer findOrCreateCustomer(Long agencyId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		}

		return findCustomerById(agencyId, customerId);
	}

	private Customer findCustomerById(Long agencyId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID = " + customerId + " was not found."));
		boolean found = false;
		for (Agency agency : customer.getAgency()) {
			if (agency.getAgencyId() == agencyId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Customer ID " + customerId + " is not a member for agency with id " + agencyId);
		}
		return customer;
	}

	@Transactional(readOnly = false)
	public CustomerData saveCustomer(Long agencyId, CustomerData customerData) {
		Agency agency = findAgencyById(agencyId);
		Long customerId = customerData.getCustomerId();
		Customer customer = findOrCreateCustomer(agencyId, customerId);
		copyCustomerFields(customer, customerData);
		agency.getCustomers().add(customer);
		customer.getAgency().add(agency);
		Customer dbCustomer = customerDao.save(customer);

		return new CustomerData(dbCustomer);
	}

	@Transactional(readOnly = false)
	public void deleteCustomerId(Long customerId) {
		Customer customer = findCustomerById(customerId);
		customerDao.delete(customer);
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID = " + customerId + " was not found."));

	}
}
