package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @ManyToOne(fetch = FetchType.LAZY) //멤버 클래스만 DB에서 조회할 수 있도록 (굳이 조인할 필요 없이) > Team은 프록시로 가져온다.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn //(name = "TEAM_ID",insertable = false, updatable = false) //insertable, updatable: 무효화시킴(조회만 가능하도록)
    private Team team;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public List<Product> getProducts() {
        return products;
    }
}
