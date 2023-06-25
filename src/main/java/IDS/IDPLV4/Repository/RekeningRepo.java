package IDS.IDPLV4.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import IDS.IDPLV4.Entity.Rekening;

@Repository
public interface RekeningRepo extends JpaRepository<Rekening, Long> {
	
	Optional<Rekening> findByIdAndDeletedDateIsNull(Long id);

    Page<Rekening> findByDeletedDateIsNull(Pageable pageable);

    default Rekening softDelete(Rekening rekening) {
    	rekening.setDeletedDate(LocalDateTime.now());
        return save(rekening);
    }
	
}
