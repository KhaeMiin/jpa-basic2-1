package hellojpa;

import javax.persistence.*;

@Entity
/**
 * @Inheritance(strategy = InheritanceType.)
 * SINGLE_TABLE: 한 테이블에 모든 자손객체 데이터 다 들어감 : 성능 향상 (@DiscriminatorColumn 생략가능 = 자동으로 테이블에 DTYPE 컬럼 들어감)
 * ㄴ 단점 : null 허용이 되야한다.(자식 엔티티 맵핑한 컬럼들 모두) 테이블이 커질 경우 (잘 없지만) 오히려 성능이 느려짐 > 즉 단순한 테이블일 경우에만 사용
 *
 * JOIN_TABLE: 부모와 자손객체 모두 따로 테이블이 생성된다. : 성능이 떨어짐 (@DiscriminatorColumn)
 * ㄴ 단점:  (대신 JOIN이 너무 많이 들어갈 수 있다.), 쿼리가 두번 나감. 쿼리가 복잡하다!!!!(이게 젤 큰 단점!!나머진 크게 그닥) 하지만 정석!!객체랑도 잘 맞음(상속)
 *
 * TABLE_PER_CLASS: 부모의 필드데이터를 자식들이 받아서 자식들만 테이블이 생성됨: 비추..별로다.부모타입으로 조회시 쿼리가 너무 많이 날라감. 비효율적이다.
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn //(name = "") : 컬럼명 바꾸기
public abstract class Item { //독단적으로 만들 수 없도록 추상클래스로 만들자!

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
