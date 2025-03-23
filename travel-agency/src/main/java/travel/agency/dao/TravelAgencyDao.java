package travel.agency.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import travel.agency.entity.Agency;

public interface TravelAgencyDao extends JpaRepository<Agency, Long> {

}
