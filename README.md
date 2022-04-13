# Spring Data JPA
## spring boot 프로젝트 생성
- Java Version : 8
- Type : gradle
- packaging : war
- 의존성 설정
    - Spring Boot DevTools
    - Lombok
    - Spring Web
    - Sping Data JPA
    - MariaDB
---
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
### 테스트 클래스 생성
```java
@SpringBootTest
public class MemoRepositoryTests {

   @Autowired
   MemoRepository memoRepository;

   @Test
   public void testClass(){
      System.out.println(memoRepository.getClass().getName());
   }
```
1.insert : save(엔티티 객체)
```java
...
   /**
    * Insert Test - save()
    */
   @Test
   public void testInsertDummies(){
        for (int i = 1; i < 101; i++) {
           Memo memo = Memo.builder().memoText("sample - " + i).build();
           memoRepository.save(memo);
        }
   }
...
```
2. select : findById(키 타입), getOne(키 타입)
    - getOne()은 더이상 사용되지 않음
```java
    /**
     * Select Test - findById()
     */
    @Test
    public void testSelect(){
        Long mno = 99L;
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=====================================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }
```
3. update : save(엔티티 객체)
```java
...
   /**
    * Update Test - save()
    */
   @Test
   public void testUpdate(){
        Memo memo = Memo.builder().mno(99L).memoText("update Text - " + 99L).build();
        System.out.println(memoRepository.save(memo));
   }
...
```
4. delete : deleteById(키 타입), delete(엔티티 객체)
```java
...
   /**
    * Delete Test - deleteById()
    */
   @Test
   public void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
   }
...
```
---
## 페이징/정렬 처리
- JPA는 Paging처리를 내부적으로 **Dialect**를 이용하여 처리
- findAll() 메서드를 사용
- MariaDB의 경우 자동으로 MariaDB를 위한 Dialect가 설정됨
```text
- 프로젝트 로딩시점에서 출력되는 로그
...
2022-04-13 20:48:09.228  INFO 14676 --- [  restartedMain] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.MariaDB106Dialect
2022-04-13 20:48:09.996  INFO 14676 --- [  restartedMain] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
...
```
---
## Pageable Interface
- 페이징 처리를 위해 **가장 중요한 인터페이스**
- 페이지 처리에 **필요한 정보를 전달**하는 용도의 타입  
---
## 페이징 처리
- 테스트 코드
   ```java
   import org.springframework.data.domain.Page;
   import org.springframework.data.domain.PageRequest;
   import org.springframework.data.domain.Pageable;
           ...
   /**
    * Paging Test - findAll(), Pageable
    */
   @Test
   public void testPageDefault(){
           // 1페이지 10개
           Pageable pageable = PageRequest.of(0, 10);
           Page<Memo> result = memoRepository.findAll(pageable);
           System.out.println(result);
           }
   ```
   💡 페이지 처리는 반드시 '0'부터 시작!
   
   - Page<엔티티 타입>
     - findAll()의 반환타입
     - 페이지 처리와 관련된 정보를 담고있다.
       1. `getTotalPages()` : 총 페이지 수
       2. `getTotalElements()` : 총 갯수
       3. `getNumber()` : 현재 페이지의 번호
       4. `getSize()` : 페이지당 데이터 개수
       5. `hasNext()` : 다음페이지 존재여부
       6. `isFirst()` : 시작페이지 여부
     - 실제 페이지의 데이터를 처리하는것은 getContent() 또는 get() 이용  
---
## 정렬조건 추가
   - org.springframework.data.domain.Sort 타입을 파라미터로 전달
   - Sort는 한 개 혹은 여러개의 필드값을 이용해서 asc 나 desc를 지정할 수 있음.
   ```java
       /**
        * Sort Test
        */
       @Test
       public void testSort(){
           Sort sort1 = Sort.by("mno").descending();
           Sort sort2 = Sort.by("memoText").ascending();
           Sort sortAll = sort1.and(sort2);
   
           Pageable pageable = PageRequest.of(0, 10, sortAll);
           Page<Memo> result = memoRepository.findAll(pageable);
           result.get().forEach(memo -> {
               System.out.println(memo);
           });
       }
   ```
---
## @Query
   - 메소드에 추가한 어노테이션을 통해서 원하는 처리가 가능
   - 필요한 데이터만 선별적으로 추출하는 기능이 가능
   - DB에 맞는 순수한 SQL(Native SQL)을 사용하는 기능
   - select 가 아닌 DML 등을 처리하는 기능(@Modifying과 함께 사용)
   
   ex) 'mno'의 역순으로 정렬하라  
   ```java
   @Query("select m from Memo m order by m.mno desc")
   List<Memo> getListDesc();
   ```

   ### 파라미터 바인딩
- `?1, ?2` 와 1부터 시작하는 파라미터의 순서를 이용하는 방식
- `:xxx` 와 같이 `:파라미터 이름`을 활용하는 방식
- `#{ }`와 같이 자바 빈 스타일을 이용하는 방식  

1. `:파라미터 이름`
```java
@Transactional
@Modifying
@Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
int updateMemoText (@Param("mno") Long mno, @Param("memoText") String memoText );
```
  

2. `#{ }`
```java
@Transactional
@Modifying
@Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno =:#{#param.mno} ")
int updateMemoText (@Param("param") Memo memo );
```