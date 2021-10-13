package com.texo.awards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.texo.awards.model.Awards;

@Repository
public interface AwardsRepository extends JpaRepository<Awards, Integer> {

	@Query(value = "SELECT * FROM AWARDS WHERE winner = 'Y' AND producer IN "
			+ "(SELECT producer FROM AWARDS ap WHERE winner = 'Y' GROUP BY producer HAVING count(1) > 1) "
			+ "ORDER BY producer, year", nativeQuery = true)
	List<Awards> getAllReWinners();
}