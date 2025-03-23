package travel.agency.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data 
public class Locations {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long locationId;
		private String locationCountry;
		private String locationCity;



		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "agency_id")
		private Agency agency;
}
