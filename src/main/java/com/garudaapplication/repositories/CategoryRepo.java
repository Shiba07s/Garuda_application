package com.garudaapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository
;

import com.garudaapplication.entities.Category;
import com.garudaapplication.payloads.CategoryDto;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

 

}
