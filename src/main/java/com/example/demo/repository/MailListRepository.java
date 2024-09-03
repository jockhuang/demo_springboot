package com.example.demo.repository;

import com.example.demo.model.MailList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Pageable sortedByPriceDescNameAsc =
 *   PageRequest.of(0, 5, Sort.by("email").descending().and(Sort.by("createDate")));
 */
public interface MailListRepository extends JpaRepository<MailList, Integer> {
    Page<MailList> findByEmailContaining(String email, Pageable pageable);
}
