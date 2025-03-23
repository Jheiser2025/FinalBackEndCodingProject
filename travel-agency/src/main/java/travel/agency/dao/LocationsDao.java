package travel.agency.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import travel.agency.entity.Locations;

public interface LocationsDao extends JpaRepository<Locations, Long> {

}
