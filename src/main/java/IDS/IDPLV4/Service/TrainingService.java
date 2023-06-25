package IDS.IDPLV4.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import IDS.IDPLV4.DTO.PageableListData;
import IDS.IDPLV4.Entity.Training;
import IDS.IDPLV4.Repository.TrainingRepo;
import IDS.IDPLV4.Utility.ApiResponse;
import IDS.IDPLV4.Utility.Validator;

@Service
public class TrainingService {
	
	@Autowired
	private TrainingRepo trainingRepository;
	
	public Training saveTraining(Training training) {
		return trainingRepository.save(training);
	}
	
	public ApiResponse<Training> updateTraining(Training training) {
        Optional<Training> existingTrainingOptional = trainingRepository.findById(training.getId());
        if (!existingTrainingOptional.isPresent() || existingTrainingOptional.get().getDeletedDate() != null) {
            return ApiResponse.failed(HttpStatus.NOT_FOUND, "Training not found with ID: " + training.getId());
        }

       
        training.setUpdateDate(LocalDateTime.now());

        Training updatedTraining = trainingRepository.save(training);
        return ApiResponse.success(updatedTraining);
    }
	
	public ApiResponse<PageableListData<Training>> getAllTraining(Pageable pageable) {
        Page<Training> trainingPage = trainingRepository.findByDeletedDateIsNull(pageable);
        List<Training> trainingList = trainingPage.getContent();


        PageableListData<Training> trainingListData = new PageableListData<>();
        trainingListData.setContent(trainingList);
        trainingListData.setPageable(trainingPage.getPageable());
        trainingListData.setLast(trainingPage.isLast());
        trainingListData.setTotalElements(trainingPage.getTotalElements());
        trainingListData.setTotalPages(trainingPage.getTotalPages());
        trainingListData.setSize(trainingPage.getSize());
        trainingListData.setNumber(trainingPage.getNumber());
        trainingListData.setSort(trainingPage.getSort());
        trainingListData.setFirst(trainingPage.isFirst());
        trainingListData.setNumberOfElements(trainingPage.getNumberOfElements());
        trainingListData.setEmpty(trainingPage.isEmpty());

        return ApiResponse.success(trainingListData);
    }
	
	public ApiResponse<Training> getTrainingById(Long id) {
        if (Validator.isNull(id)) {
            return ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        Optional<Training> trainingOptional = trainingRepository.findByIdAndDeletedDateIsNull(id);
        if (!trainingOptional.isPresent()) {
            return ApiResponse.failed(HttpStatus.NOT_FOUND, "Training not found with ID: " + id);
        }

        Training training = trainingOptional.get();
        return ApiResponse.success(training);
    }
	
	public ApiResponse<String> deleteTraining(Long id) {
        if (Validator.isNull(id)) {
            return ApiResponse.failed(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        Optional<Training> trainingOptional = trainingRepository.findByIdAndDeletedDateIsNull(id);
        if (!trainingOptional.isPresent()) {
            return ApiResponse.failed(HttpStatus.NOT_FOUND, "Training not found with ID: " + id);
        }

        Training training = trainingOptional.get();
        trainingRepository.softDelete(training);

        return ApiResponse.success("Success");
    }
}
