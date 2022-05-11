package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team); //알아서 pk값 꺼내서 fk값으로 씀
            em.persist(member);

            //연관관계 편의메서드에 추가하여 아래 코드 지워도됨
//            team.getMembers().add(member); //DB에는 상관이 없다. 읽기전용으로만 사용하기 (객체지향적으로 생각했을 때 양방향: 양쪽에 다 값 넣는게 낫다.)

//            em.flush(); //SQL 저장소에 있는 쿼리 다 DB로 보냄
//            em.clear(); //영속성 컨텍스트 모두 초기화 (캐시도 비워짐)

            System.out.println("=============================================");
            Team findTeam = em.find(Team.class, team.getId()); //flush, clear 안한 상태의 1차 캐시
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();//쓰기지연 SQL 저장소에 SQL문을 DB에 보냄
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
