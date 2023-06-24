package IDS.IDPLV4.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import IDS.IDPLV4.DTO.KaryawanListData;
import IDS.IDPLV4.Entity.DetailKaryawan;
import IDS.IDPLV4.Entity.Karyawan;
import IDS.IDPLV4.Repository.KaryawanRepo;
import IDS.IDPLV4.Utility.ApiResponse;
import IDS.IDPLV4.Utility.Validator;

@Service
public class KaryawanService {
	
	@Autowired
	private KaryawanRepo karyawanRepository;
	
	public Karyawan saveKaryawan(Karyawan karyawan) {
        return karyawanRepository.save(karyawan);
    }
	
	public ApiResponse<Karyawan> updateKaryawan(Karyawan karyawan) {
        Optional<Karyawan> existingKaryawanOptional = karyawanRepository.findById(karyawan.getId());
        if (!existingKaryawanOptional.isPresent()) {
            return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with id: " + karyawan.getId());
        }

        Karyawan existingKaryawan = existingKaryawanOptional.get();
        existingKaryawan.setNama(karyawan.getNama());
        existingKaryawan.setDob(karyawan.getDob());
        existingKaryawan.setStatus(karyawan.getStatus());
        existingKaryawan.setAlamat(karyawan.getAlamat());

        DetailKaryawan existingDetailKaryawan = existingKaryawan.getDetailKaryawan();
        DetailKaryawan updatedDetailKaryawan = karyawan.getDetailKaryawan();
        if (Validator.isNotNull(existingDetailKaryawan) && Validator.isNotNull(updatedDetailKaryawan)) {
            existingDetailKaryawan.setNik(updatedDetailKaryawan.getNik());
            existingDetailKaryawan.setNpwp(updatedDetailKaryawan.getNpwp());
        }
        
        existingKaryawan.setUpdateDate(LocalDateTime.now());

        Karyawan updatedKaryawan = karyawanRepository.save(existingKaryawan);
        return ApiResponse.success(updatedKaryawan);
    }
	
	public ApiResponse<KaryawanListData> getAllKaryawan(Pageable pageable) {
        Page<Karyawan> karyawanPage = karyawanRepository.findAll(pageable);
        List<Karyawan> karyawanList = karyawanPage.getContent();

        KaryawanListData karyawanListData = new KaryawanListData();
        karyawanListData.setContent(karyawanList);
        karyawanListData.setPageable(karyawanPage.getPageable());
        karyawanListData.setLast(karyawanPage.isLast());
        karyawanListData.setTotalElements(karyawanPage.getTotalElements());
        karyawanListData.setTotalPages(karyawanPage.getTotalPages());
        karyawanListData.setSize(karyawanPage.getSize());
        karyawanListData.setNumber(karyawanPage.getNumber());
        karyawanListData.setSort(karyawanPage.getSort());
        karyawanListData.setFirst(karyawanPage.isFirst());
        karyawanListData.setNumberOfElements(karyawanPage.getNumberOfElements());
        karyawanListData.setEmpty(karyawanPage.isEmpty());

        return ApiResponse.success(karyawanListData);
    }
	
	public ApiResponse<Karyawan> getKaryawanById(Long id) {
	    if (Validator.isNull(id)) {
	        return ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID");
	    }

	    Optional<Karyawan> karyawanOptional = karyawanRepository.findById(id);
	    if (!karyawanOptional.isPresent()) {
	        return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with ID: " + id);
	    }

	    Karyawan karyawan = karyawanOptional.get();
	    return ApiResponse.success(karyawan);
	}
	
	public ApiResponse<String> deleteKaryawan(Long id) {
        Optional<Karyawan> karyawanOptional = karyawanRepository.findById(id);
        if (!karyawanOptional.isPresent()) {
            return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with id: " + id);
        }

        Karyawan karyawan = karyawanOptional.get();
        karyawan.setDeletedDate(LocalDateTime.now());
        karyawanRepository.save(karyawan);

        return ApiResponse.success("Sukses");
    }

}
