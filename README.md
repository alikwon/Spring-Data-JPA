# Spring Data JPA
## Entity Class

```java
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "TBL_MEMO")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

    @Column(columnDefinition = "varchar(255) default 'Yes'")
    private String memoText2;

}
```

1. **@Entity**
    - 해당 클래스는 엔티티를 위한 클래스
    - 해당 클래스의 인스턴스들이 JPA로 관리되는 엔티티 객체라는 것을 의미
2. **@Table**
    - DB 상에서 엔티티클래스를 어떤 테이블로 생성할 것인지에 대한 정보
    - `name`: 매핑할 테이블의 이름
    - `catalog` : DB의 catalog를 맵핑
    - `schema` : DB 스키마와 맵핑
    - `uniqueConstraint` : DDL 쿼리를 작성할 때 제약 조건을 생성
3. **@Id**
    - @Entity가 붙은 클래스는 Primary Key에 해당하는 특정 필드를 @Id로 지정
    - 자동으로 생성되는 번호를 사용하기 위해서 @Generated Value라는 어노테이션을 활용
4. **@GeneratedValue**
    - PK를 자동으로 생성하고자 할 때 사용
    - MySQL , MariaDB는 ‘auto increment’ 를 기본으로 사용
    - Oracle 은 별도의 번호를 위한 별도의 테이블 생성
5. **@Column**
    - 객체 필드와 DB 테이블 컬럼을 맵핑
    - `name` : 맵핑할 테이블의 컬럼 이름을 지정
    - `insertable` : 엔티티 저장시 선언된 필드도 같이 저장
    - `updateable` : 엔티티 수정시 이 필드를 함께 수정
    - `table` : 지정한 필드를 다른 테이블에 맵핑
    - `nullable` : NULL을 허용할지, 허용하지 않을지 결정
    - `unique` : 제약조건을 걸 때 사용
    - `columnDefinition` : DB 컬럼 정보를 직접적으로 지정할 때 사용
    - `length` : varchar의 길이를 조정 (기본값: 255)
    - `precsion`, `scale` : BigInteger, BigDecimal 타입에서 사용합니다. 각각 소수점 포함 자리수, 소수의 자리수를 의미합니다

---

## JPA 관련 설정

```jsx
# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
```

1. **spring.jpa.hibernate.ddl-auto** 
    - 프로젝트 실행 시에 자동으로 DDL 을 생성할 것인지를 결정하는설정
    - `create` : 매번 테이블생성을 새로 시도
    - `update` : 변경이 필요한 경우 alter, 테이블이 없는 경우 create
    - `create-drop` : create와 같으나 종료시점에 테이블 DROP
    - `validate` : 엔티티와 테이블이 정상 매핑되었는지만 확인
2. **spring.jpa.properties.hibernate.format_sql**
    - 실제 JPA 구현체인 Hibernate가 동작하면서 발생하는 SQL을 포맷팅해서 출력
3. **spring.jpa.show-sql**
    - JPA 처리 시 발생하는 SQL을 보여줄 것인지 결정

---
## JpaRepository
```java
import org.alikwon.jpa.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
```
1. JpaRepository를 상속받는다.
2. JpaRepository를<엔티티의 클래스타입, @Id의 타입>
3. 인터페이스 선언만으로도 자동으로 스프링의 빈으로 등록
    - 스프링이 내부적으로 인터페이스 타입에 맞는 객체를 생성하여 빈으로 등록함.
---
## CRUD
- insert : save(엔티티 객체)
- select : findById(키 타입), getOne(키 타입)
    - getOne()은 더이상 사용되지 않음
- update : save(엔티티 객체)
- delete : deleteById(키 타입), delete(엔티티 객체)

<aside>
💡 

</aside>
