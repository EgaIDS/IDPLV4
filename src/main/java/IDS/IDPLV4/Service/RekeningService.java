package IDS.IDPLV4.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import IDS.IDPLV4.DTO.DetailKaryawanDTO;
import IDS.IDPLV4.DTO.KaryawanDTO;
import IDS.IDPLV4.DTO.KaryawanRekeningDTO;
import IDS.IDPLV4.DTO.PageableListData;
import IDS.IDPLV4.DTO.RekeningDTO;
import IDS.IDPLV4.Entity.DetailKaryawan;
import IDS.IDPLV4.Entity.Karyawan;
import IDS.IDPLV4.Entity.Rekening;
import IDS.IDPLV4.Repository.KaryawanRepo;
import IDS.IDPLV4.Repository.RekeningRepo;
import IDS.IDPLV4.Utility.ApiResponse;
import IDS.IDPLV4.Utility.Validator;

@Service
public class RekeningService {
	
	@Autowired
	private RekeningRepo rekeningRepository;
	@Autowired
	private KaryawanRepo karyawanRepository;
	
	public ApiResponse<Map<String, Object>> saveRekening(Rekening rekening) {
		Optional<Karyawan> optionalKaryawan = karyawanRepository.findById(rekening.getKaryawan().getId());
        if (!optionalKaryawan.isPresent() || optionalKaryawan.get().getDeletedDate() != null) {
        	return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with ID: " + rekening.getKaryawan().getId());
        }
        
        rekening.setKaryawan(optionalKaryawan.get());
        Rekening resultRekening = rekeningRepository.save(rekening);
        return ApiResponse.buildRekeningResponse(resultRekening);
    }
	
	public ApiResponse<Map<String, Object>> updateRekening(Rekening rekening){
		Optional<Rekening> optionalRekening = rekeningRepository.findById(rekening.getId());
		if (!optionalRekening.isPresent() || optionalRekening.get().getDeletedDate() != null) {
			return ApiResponse.failed(HttpStatus.NOT_FOUND, "Rekening not found with ID: " + rekening.getId());
		}
		Optional<Karyawan> optionalKaryawan = karyawanRepository.findById(rekening.getKaryawan().getId());
        if (!optionalKaryawan.isPresent() || optionalKaryawan.get().getDeletedDate() != null) {
        	return ApiResponse.failed(HttpStatus.NOT_FOUND, "Karyawan not found with ID: " + rekening.getKaryawan().getId());
        }
        
        rekening.setUpdateDate(LocalDateTime.now());
        rekening.setKaryawan(optionalKaryawan.get());
        rekening.setCreatedDate(optionalRekening.get().getCreatedDate());
        Rekening resultRekening = rekeningRepository.save(rekening);

        return ApiResponse.buildRekeningResponse(resultRekening);
        
		
	}
	
	public ApiResponse<PageableListData<RekeningDTO>> getAllRekening(Pageable pageable) {
        Page<Rekening> RekeningPage = rekeningRepository.findByDeletedDateIsNull(pageable);
        List<Rekening> RekeningList = RekeningPage.getContent();
        List<RekeningDTO> rekeningDTOList = RekeningList.stream()
                .map(this::convertToRekeningDTO)
                .collect(Collectors.toList());

        PageableListData<RekeningDTO> RekeningListData = new PageableListData();
        RekeningListData.setContent(rekeningDTOList);
        RekeningListData.setPageable(RekeningPage.getPageable());
        RekeningListData.setLast(RekeningPage.isLast());
        RekeningListData.setTotalElements(RekeningPage.getTotalElements());
        RekeningListData.setTotalPages(RekeningPage.getTotalPages());
        RekeningListData.setSize(RekeningPage.getSize());
        RekeningListData.setNumber(RekeningPage.getNumber());
        RekeningListData.setSort(RekeningPage.getSort());
        RekeningListData.setFirst(RekeningPage.isFirst());
        RekeningListData.setNumberOfElements(RekeningPage.getNumberOfElements());
        RekeningListData.setEmpty(RekeningPage.isEmpty());

        return ApiResponse.success(RekeningListData);
    }
	
	private RekeningDTO convertToRekeningDTO(Rekening rekening) {
		Optional<Karyawan> optionalKaryawan = karyawanRepository.findById(rekening.getKaryawan().getId());
		RekeningDTO rekeningDTO = new RekeningDTO();
	    rekeningDTO.setCreatedDate(rekening.getCreatedDate());
	    rekeningDTO.setUpdatedDate(rekening.getUpdateDate());
	    rekeningDTO.setDeletedDate(rekening.getDeletedDate());
	    rekeningDTO.setId(rekening.getId());
	    rekeningDTO.setNama(rekening.getNama());
	    rekeningDTO.setJenis(rekening.getJenis());
	    rekeningDTO.setRekening(rekening.getRekening());
	    rekeningDTO.setAlamat(optionalKaryawan.get().getAlamat());
	    rekeningDTO.setKaryawan(convertToKaryawanRekeningDTO(rekening.getKaryawan()));
//	    karyawanDTO.setDetailKaryawan(convertToDetailKaryawanDTO(karyawan.getDetailKaryawan()));
	    
	    
	    return rekeningDTO;
	}
	
	private KaryawanRekeningDTO convertToKaryawanRekeningDTO(Karyawan karyawan) {
		KaryawanRekeningDTO karyawanRekeningDTO = new KaryawanRekeningDTO();
		karyawanRekeningDTO.setId(karyawan.getId());
	    karyawanRekeningDTO.setNama(karyawan.getNama());
	    
	    return karyawanRekeningDTO;
	}
	
	
	public ApiResponse<RekeningDTO> getRekeningById(Long id) {
	    if (Validator.isNull(id)) {
	        return ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID");
	    }

	    Optional<Rekening> rekeningOptional = rekeningRepository.findByIdAndDeletedDateIsNull(id);
	    if (!rekeningOptional.isPresent()) {
	        return ApiResponse.failed(HttpStatus.NOT_FOUND, "Rekening not found with ID: " + id);
	    }

	    Rekening rekening = rekeningOptional.get();
	    RekeningDTO rekeningDTO = convertToRekeningDTO(rekening);
	    return ApiResponse.success(rekeningDTO);
	}
	
	public ApiResponse<String> deleteRekening(Long id) {
		if (Validator.isNull(id)) {
	        return ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID");
	    }

	    Optional<Rekening> rekeningOptional = rekeningRepository.findByIdAndDeletedDateIsNull(id);
	    if (!rekeningOptional.isPresent()) {
	        return ApiResponse.failed(HttpStatus.NOT_FOUND, "Rekening not found with ID: " + id);
	    }

	    Rekening rekening = rekeningOptional.get();
	    rekeningRepository.softDelete(rekening);

        return ApiResponse.success("Sukses");
    }
}
