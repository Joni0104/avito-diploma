package com.avito.diploma.repository;

import com.avito.diploma.entity.Ad;
import com.avito.diploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findByAuthor(User author);

    List<Ad> findByAuthorId(Integer authorId);
}