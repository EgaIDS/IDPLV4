package IDS.IDPLV4.Controller;

import java.util.List;
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

import IDS.IDPLV4.Service.KaryawanService;
import IDS.IDPLV4.Utility.ApiResponse;
import IDS.IDPLV4.Utility.Validator;
import IDS.IDPLV4.DTO.KaryawanListData;
import IDS.IDPLV4.Entity.Karyawan;

@RestController
@RequestMapping("/v1/idstar/karyawan")
public class KaryawanController {

	@Autowired
	private KaryawanService karyawanService;

	@PostMapping("/save")
	public ResponseEntity<ApiResponse<Karyawan>> saveKaryawan(@RequestBody Karyawan karyawan) {
		Karyawan savedKaryawan = karyawanService.saveKaryawan(karyawan);
		ApiResponse<Karyawan> response = new ApiResponse<>(200, savedKaryawan, "sukses");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<Karyawan>> updateKaryawan(@RequestBody Karyawan karyawan) {
	    ApiResponse<Karyawan> response = karyawanService.updateKaryawan(karyawan);
	    if (response.getCode() == 200) {
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(response.getCode()).body(response);
	    }
	}
	
	@GetMapping("/list")
    public ResponseEntity<ApiResponse<KaryawanListData>> getKaryawanList(
            @RequestParam int page,
            @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
        ApiResponse<KaryawanListData> response = karyawanService.getAllKaryawan(pageable);
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Karyawan>> getKaryawanById(@PathVariable("id") Long id) {
	    ApiResponse<Karyawan> response = karyawanService.getKaryawanById(id);
	    return ResponseEntity.ok(response);
	}
	
	 @DeleteMapping("/delete")
	    public ResponseEntity<ApiResponse<String>> deleteKaryawan(@RequestBody Map<String, Long> request) {
	        Long id = request.get("id");
	        if (Validator.isNull(id)) {
	            return ResponseEntity.badRequest().body(ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID"));
	        }

	        ApiResponse<String> response = karyawanService.deleteKaryawan(id);
	        if (response.getCode() == 200) {
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(response.getCode()).body(response);
	        }
	    }

}
