package org.alikwon.jpa.entity;

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

//    @Column(columnDefinition = "varchar(255) default 'Yes'")
//    private String memoText2;

}
