package cl.ey.userapi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserModel {

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

	@Type(type = "uuid-char")
	private UUID uuid;
	private String name;
	private String email;
	private String password;
	private String token;
	private Date lastLogin;
	private Boolean active;

	@OneToMany(mappedBy = "user")
	private List<PhoneModel> phones;

	@PrePersist
	void lastLogin() {
		if(this.createdAt==null){
			this.createdAt = new Date();
		}
		this.updatedAt = this.createdAt;
		this.lastLogin = this.createdAt;
	}
}
