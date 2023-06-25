package IDS.IDPLV4.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import IDS.IDPLV4.DTO.DetailKaryawanDTO;
import IDS.IDPLV4.DTO.KaryawanDTO;
import IDS.IDPLV4.DTO.PageableListData;
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
        if (!existingKaryawanOptional.isPresent() || existingKaryawanOptional.get().getDeletedDate() != null) {
            return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with ID: " + karyawan.getId());
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
	
	public ApiResponse<PageableListData<KaryawanDTO>> getAllKaryawan(Pageable pageable) {
        Page<Karyawan> karyawanPage = karyawanRepository.findByDeletedDateIsNull(pageable);
        List<Karyawan> karyawanList = karyawanPage.getContent();
//        for (Karyawan karyawan : karyawanList) {
//			System.out.println(karyawan.getId());
//			System.out.println(karyawan.getDetailKaryawan().getNik());
//		}
        List<KaryawanDTO> karyawanDTOList = karyawanList.stream()
                .map(this::convertToKaryawanDTO)
                .collect(Collectors.toList());

        PageableListData<KaryawanDTO> karyawanListData = new PageableListData();
        karyawanListData.setContent(karyawanDTOList);
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
	
	public ApiResponse<KaryawanDTO> getKaryawanById(Long id) {
	    if (Validator.isNull(id)) {
	        return ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID");
	    }

	    Optional<Karyawan> karyawanOptional = karyawanRepository.findByIdAndDeletedDateIsNull(id);
	    if (!karyawanOptional.isPresent()) {
	        return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with ID: " + id);
	    }

	    Karyawan karyawan = karyawanOptional.get();
	    KaryawanDTO karyawanDTO = convertToKaryawanDTO(karyawan);
	    return ApiResponse.success(karyawanDTO);
	}
	
	public ApiResponse<String> deleteKaryawan(Long id) {
		if (Validator.isNull(id)) {
	        return ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID");
	    }

	    Optional<Karyawan> karyawanOptional = karyawanRepository.findByIdAndDeletedDateIsNull(id);
	    if (!karyawanOptional.isPresent()) {
	        return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with ID: " + id);
	    }

	    Karyawan karyawan = karyawanOptional.get();
	    karyawanRepository.softDelete(karyawan);

        return ApiResponse.success("Sukses");
    }
	
	private KaryawanDTO convertToKaryawanDTO(Karyawan karyawan) {
	    KaryawanDTO karyawanDTO = new KaryawanDTO();
	    karyawanDTO.setCreated_date(karyawan.getCreatedDate());
	    karyawanDTO.setUpdated_date(karyawan.getUpdateDate());
	    karyawanDTO.setDeleted_date(karyawan.getDeletedDate());
	    karyawanDTO.setId(karyawan.getId());
	    karyawanDTO.setNama(karyawan.getNama());
	    karyawanDTO.setDob(karyawan.getDob());
	    karyawanDTO.setStatus(karyawan.getStatus());
	    karyawanDTO.setAlamat(karyawan.getAlamat());
	    karyawanDTO.setDetailKaryawan(convertToDetailKaryawanDTO(karyawan.getDetailKaryawan()));
	    
	    
	    return karyawanDTO;
	}
	
	private DetailKaryawanDTO convertToDetailKaryawanDTO(DetailKaryawan detailKaryawan) {
	    DetailKaryawanDTO detailKaryawanDTO = new DetailKaryawanDTO();
	    detailKaryawanDTO.setCreated_date(detailKaryawan.getCreatedDate());
	    detailKaryawanDTO.setUpdated_date(detailKaryawan.getUpdateDate());
	    detailKaryawanDTO.setDeleted_date(detailKaryawan.getDeletedDate());
	    detailKaryawanDTO.setId(detailKaryawan.getId());
	    detailKaryawanDTO.setNik(detailKaryawan.getNik());
	    detailKaryawanDTO.setNpwp(detailKaryawan.getNpwp());
	    
	    
	    return detailKaryawanDTO;
	}

}
