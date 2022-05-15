package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /**
     * cascade : 다른 엔티티와 연관관계가 있을 경우 사용하지 말자.
     * 소유자가 하나일 경우 사용하자. (단일 엔티티에 완전히 종속적일 경우에만)
     *  orphanRemoval = true: 그니까 부모 삭제하면 부모 PK 연결돼있는 자식도 삭제된다는거 > 부모삭제되면 자식은 고아됨. 그래서 삭제됨
     *  이것 역시 소유자가 하나일 경우에만 사용하자.
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true) //부모 엔티티 영속컨테스트 저장시 아래 자식?join?도 함께 저장
    private List<Child> childList = new ArrayList<>();

    //연관관계 편의메서드
    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
