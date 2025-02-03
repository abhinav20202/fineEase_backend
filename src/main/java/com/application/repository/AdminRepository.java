package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
