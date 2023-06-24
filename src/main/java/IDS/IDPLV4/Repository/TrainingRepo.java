package IDS.IDPLV4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import IDS.IDPLV4.Entity.Training;

@Repository
public interface TrainingRepo extends JpaRepository<Training, Long> {

}
