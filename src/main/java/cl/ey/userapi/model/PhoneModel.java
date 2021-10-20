package cl.ey.userapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "phone")
public class PhoneModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	private String number;
	private String citycode;
	private String contrycode;

	@ManyToOne
	@JsonIgnore
	private UserModel user;

	public PhoneModel(){}

	public PhoneModel(String number, String citycode, String contrycode) {
		this.number = number;
		this.citycode = citycode;
		this.contrycode = contrycode;
	}

	@PrePersist
	void lastLogin() {
		if(this.createdAt==null){
			this.createdAt = new Date();
		}
		this.updatedAt = this.createdAt;
	}
}
