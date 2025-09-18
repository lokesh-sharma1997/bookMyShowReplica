package com.bookmyshow.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Languages;

public interface LanguagesRepository extends JpaRepository<Languages, Integer> {
	Optional<Languages> findByLanguageName(String languageName);
}
