package IDS.IDPLV4.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import IDS.IDPLV4.Entity.Karyawan;

@Repository
public interface KaryawanRepo extends JpaRepository<Karyawan, Long> {
	
	Optional<Karyawan> findByIdAndDeletedDateIsNull(Long id);

//	@Query("SELECT DISTINCT k FROM Karyawan k JOIN FETCH k.detailKaryawan WHERE k.deletedDate IS NULL")
    Page<Karyawan> findByDeletedDateIsNull(Pageable pageable);

    default Karyawan softDelete(Karyawan karyawan) {
        karyawan.setDeletedDate(LocalDateTime.now());
        return save(karyawan);
    }

}
