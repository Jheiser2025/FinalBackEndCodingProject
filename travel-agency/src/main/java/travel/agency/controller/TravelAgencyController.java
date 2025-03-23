package travel.agency.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import travel.agency.controller.model.TravelAgencyData;
import travel.agency.controller.model.TravelAgencyData.CustomerData;
import travel.agency.controller.model.TravelAgencyData.LocationData;
import travel.agency.service.TravelAgencyService;

@RestController
@RequestMapping("/travel_agency")
@Slf4j
public class TravelAgencyController {

	@Autowired
	private TravelAgencyService travelAgencyService;
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public TravelAgencyData insertAgency(@RequestBody TravelAgencyData travelAgencyData) {
		log.info("Creating travel agency {}",travelAgencyData);
		return travelAgencyService.saveAgency(travelAgencyData);
}
	@PutMapping("/{agencyId}")
	public TravelAgencyData updateAgency(@PathVariable Long agencyId, @RequestBody TravelAgencyData travelAgencyData) {
		travelAgencyData.setAgencyId(agencyId);
		log.info("Updating agency ID {}",travelAgencyData);
				return travelAgencyService.saveAgency(travelAgencyData);
			}
	@PostMapping("/{agencyId}/locations")
	@ResponseStatus(code=HttpStatus.CREATED)
	public LocationData addLocationToAgency(@PathVariable Long agencyId,@RequestBody LocationData locationData) {
		log.info("Adding location {} to pet store with ID={}", locationData,agencyId);
		return travelAgencyService.saveLocations(agencyId,locationData);
}
	@PostMapping("/{agencyId}/customer")
	@ResponseStatus(code=HttpStatus.CREATED)
	public CustomerData addCustomerToAgency(@PathVariable Long agencyId,@RequestBody CustomerData customerData) {
		log.info("Adding customer {} to travel agency with ID={}", customerData,agencyId);
		return travelAgencyService.saveCustomer(agencyId,customerData);
}
	@GetMapping
	public List<TravelAgencyData> retrieveAllTravelAgency(){
		log.info("Retrieve all of the travel agency's  please");
		return travelAgencyService.retrieveAllAgencies();
}
	@GetMapping("/{agencyId}")
	public TravelAgencyData retrieveAgencyById(@PathVariable Long agencyId) {
		log.info("Please retrieve the following travek agency Id{}=",agencyId);
		return travelAgencyService.retrieveAgencyById(agencyId);
	}
@DeleteMapping("/{agencyId}")
public Map<String, String> deleteAgencyById(@PathVariable Long agencyId) {
	log.info("Deleting travel agency with id{}", agencyId);
	travelAgencyService.deleteAgencyId(agencyId);
	 return Map.of("message", "Travel Agency with ID = " + agencyId + "deleted");
}
@DeleteMapping("/{agencyId}/customer")
public Map<String, String> deleteCustomerById(@PathVariable Long customerId) {
	log.info("Deleting customer with id{}", customerId);
	travelAgencyService.deleteCustomerId(customerId);
	 return Map.of("message", "Customer with ID = " + customerId + "deleted");	

}
}
