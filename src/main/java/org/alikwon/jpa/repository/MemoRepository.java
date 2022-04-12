package org.alikwon.jpa.repository;

import org.alikwon.jpa.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
