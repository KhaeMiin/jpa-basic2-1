package hellojpa;

import org.hibernate.Hibernate;

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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("hello");
            member.setTeam(team);
            em.persist(member);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member memberB = new Member();
            memberB.setUsername("hello");
            memberB.setTeam(teamB);
            em.persist(memberB);

            em.flush();
            em.clear();

            /**
             * getReference 직접적인 사용은 거의 하지 않는다.
             * 하지만 프록시를 이해해야 지연 로딩과 즉시 로딩을 이해할 수 있다.
             */
/*            Member refMember = em.getReference(Member.class, member.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass());
//            refMember.getUsername(); //이용한 프록시 강제초기화 (무식한 방법) - JPA만

            Hibernate.initialize(refMember); //하이버네이트를 강제 초기화*/

            Member m = em.find(Member.class, member.getId());


            /**
             * Member.java 에서 Team 필드변수
             * @ManyToOne(fetch = FetchType.LAZY) //멤버 클래스만 DB에서 조회할 수 있도록 (굳이 조인할 필요 없이) > Team은 프록시로 가져온다.
             * @ManyToOne(fetch = FetchType.EAGER) //즉시로딩 > 바로 가져옴(member, team 바로 조인해서 가져온다)_ Member조회시 Team도 조회가능
             * 실무에서는 가급적 지연 로딩만 사용
             * 즉시로딩 적용하면 예상치 못한 SQL 발생
             * @ManyToOne, @OneToOne : 기본이 지연로딩 (LAZY로 설정하기)
             * @OneToMany, @ManyToMany : 기본이 지연로딩
             */

            System.out.println("m.getTeam() = " + m.getTeam().getClass()); //Team 프록시로 가져옴

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList(); //즉시 로딩시 쿼리가 많이 나가게 됨..

            System.out.println("====================================");
            m.getTeam().getName(); //team을 사용하는 시점에 초기화됨(쿼리 나감) > LAZY 사용시
            System.out.println(m.getTeam().getName());
            System.out.println("====================================");

            tx.commit();//쓰기지연 SQL 저장소에 SQL문을 DB에 보냄
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();

    }

}
