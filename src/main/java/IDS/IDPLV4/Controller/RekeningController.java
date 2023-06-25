package IDS.IDPLV4.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import IDS.IDPLV4.DTO.PageableListData;
import IDS.IDPLV4.DTO.RekeningDTO;
import IDS.IDPLV4.Entity.Karyawan;
import IDS.IDPLV4.Entity.Rekening;
import IDS.IDPLV4.Service.RekeningService;
import IDS.IDPLV4.Utility.ApiResponse;
import IDS.IDPLV4.Utility.Validator;

@RestController
@RequestMapping("/v1/idstar/rekening")
public class RekeningController {
	
	@Autowired
	private RekeningService rekeningService;
	
	
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<Map<String, Object>>> saveRekening(@RequestBody Rekening rekening){
		ApiResponse<Map<String, Object>> response = rekeningService.saveRekening(rekening);
		if (response.getCode() == HttpStatus.OK.value()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(response.getCode()).body(response);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse<Map<String, Object>>> updateRekening(@RequestBody Rekening rekening) {
	    ApiResponse<Map<String, Object>> response = rekeningService.updateRekening(rekening);
	    if (response.getCode() == HttpStatus.OK.value()) {
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(response.getCode()).body(response);
	    }
	}
	
	@GetMapping("/list")
    public ResponseEntity<ApiResponse<PageableListData<RekeningDTO>>> getKaryawanList(
            @RequestParam int page,
            @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		ApiResponse<PageableListData<RekeningDTO>> response = rekeningService.getAllRekening(pageable);
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RekeningDTO>> getRekeningById(@PathVariable("id") Long id) {
	    ApiResponse<RekeningDTO> response = rekeningService.getRekeningById(id);
	    return ResponseEntity.ok(response);
	}
	
	 @DeleteMapping("/delete")
	    public ResponseEntity<ApiResponse<String>> deleteKaryawan(@RequestBody Map<String, Long> request) {
	        Long id = request.get("id");
	        if (Validator.isNull(id)) {
	            return ResponseEntity.badRequest().body(ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID"));
	        }

	        ApiResponse<String> response = rekeningService.deleteRekening(id);
	        if (response.getCode() == HttpStatus.OK.value()) {
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(response.getCode()).body(response);
	        }
	    }

}
