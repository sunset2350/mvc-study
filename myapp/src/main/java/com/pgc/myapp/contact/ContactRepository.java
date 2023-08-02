package com.pgc.myapp.contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

    Page<Contact> findByNameContaining(String name, Pageable page);

    Page<Contact> findByNameContainsOrPhoneContains(String name, String phone, Pageable pageable);

}


