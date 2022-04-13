package org.alikwon.jpa.repository;

import org.alikwon.jpa.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    /**
     * Insert Test - save()
     */
    @Test
    public void testInsertDummies() {
        for (int i = 1; i < 101; i++) {
            Memo memo = Memo.builder().memoText("sample - " + i).build();
            memoRepository.save(memo);
        }
    }

    /**
     * Select Test - findById()
     */
    @Test
    public void testSelect() {
        Long mno = 99L;
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=====================================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    /**
     * Update Test - save()
     */
    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(99L).memoText("update Text - " + 99L).build();
        System.out.println(memoRepository.save(memo));
    }

    /**
     * Delete Test - deleteById()
     */
    @Test
    public void testDelete() {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    /**
     * Paging Test - findAll(), Pageable
     * - 페이지 처리는 반드시 '0'부터 시작!
     */
    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
    }

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
}
