package IDS.IDPLV4.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import IDS.IDPLV4.Entity.Training;

@Repository
public interface TrainingRepo extends JpaRepository<Training, Long> {

	Optional<Training> findByIdAndDeletedDateIsNull(Long id);

    Page<Training> findByDeletedDateIsNull(Pageable pageable);

    default Training softDelete(Training training) {
    	training.setDeletedDate(LocalDateTime.now());
        return save(training);
    }
}
