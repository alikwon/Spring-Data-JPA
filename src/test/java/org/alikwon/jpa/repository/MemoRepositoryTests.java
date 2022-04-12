package org.alikwon.jpa.repository;

import org.alikwon.jpa.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

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

    /**
     * Update Test - save()
     */
    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(99L).memoText("update Text - " + 99L).build();
        System.out.println(memoRepository.save(memo));
    }

    /**
     * Delete Test - deleteById()
     */
    @Test
    public void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }
}
