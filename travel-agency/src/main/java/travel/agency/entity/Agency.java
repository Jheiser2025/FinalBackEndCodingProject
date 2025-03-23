package travel.agency.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity

public class Agency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long agencyId;
	private  String agencyName;
	private  String agencyEmail;
	private  String agencyPhone;

@ManyToMany(cascade = CascadeType.PERSIST)
@JoinTable(name ="agency_customer", joinColumns = @JoinColumn(name =" agency_id"), 
inverseJoinColumns = @JoinColumn (name = "customer_id"))

private Set<Customer> customers = new HashSet<>();

@OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = true)
@EqualsAndHashCode.Exclude
@ToString.Exclude
private Set<Locations> locations= new HashSet<>();

}
