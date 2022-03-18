package pl.makao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.makao.entity.Hand;

@Repository
public interface HandRepository extends JpaRepository<Hand, Integer> {
}
