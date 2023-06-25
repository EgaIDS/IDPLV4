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
import IDS.IDPLV4.Entity.Training;
import IDS.IDPLV4.Service.TrainingService;
import IDS.IDPLV4.Utility.ApiResponse;
import IDS.IDPLV4.Utility.Validator;

@RestController
@RequestMapping("/v1/idstar/training")
public class TrainingController {

	@Autowired
	private TrainingService trainingService;
	
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<Training>> saveKaryawan(@RequestBody Training training) {
		Training savedKaryawan = trainingService.saveTraining(training);
		ApiResponse<Training> response = new ApiResponse<>(200, savedKaryawan, "sukses");
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse<Training>> updateKaryawan(@RequestBody Training training) {
	    ApiResponse<Training> response = trainingService.updateTraining(training);
	    if (response.getCode() == HttpStatus.OK.value()) {
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(response.getCode()).body(response);
	    }
	}
	
	@GetMapping("/list")
    public ResponseEntity<ApiResponse<PageableListData<Training>>> getKaryawanList(
            @RequestParam int page,
            @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
        ApiResponse<PageableListData<Training>> response = trainingService.getAllTraining(pageable);
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Training>> getKaryawanById(@PathVariable("id") Long id) {
	    ApiResponse<Training> response = trainingService.getTrainingById(id);
	    return ResponseEntity.ok(response);
	}
	
	 @DeleteMapping("/delete")
	    public ResponseEntity<ApiResponse<String>> deleteKaryawan(@RequestBody Map<String, Long> request) {
	        Long id = request.get("id");
	        if (Validator.isNull(id)) {
	            return ResponseEntity.badRequest().body(ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID"));
	        }

	        ApiResponse<String> response = trainingService.deleteTraining(id);
	        if (response.getCode() == HttpStatus.OK.value()) {
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(response.getCode()).body(response);
	        }
	    }
}
