# jpa-basic2-1
JPA 복습하기 1


※em: EntityManager em = emf.createEntityManager(); <br>
※tx: EntityTransaction tx = em.getTransaction();

- em.persist(member); //영속  
- em.detach(member); //영속상태에서 빠짐(더이상 JPA에서 관리하지 않는다.) -> 준영속 상태  
- em.flush(); //쿼리 즉시 나감(1차캐시는 유지)  
- em.clear(); //영속성 컨텍스트를 완전히 초기화
- tx.commit();//쓰기지연 SQL 저장소에 SQL문을 DB에 보냄
