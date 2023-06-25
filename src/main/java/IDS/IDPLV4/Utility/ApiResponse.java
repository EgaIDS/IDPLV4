package IDS.IDPLV4.Utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import IDS.IDPLV4.Entity.Rekening;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	private int code;
    private T data;
    private String status;

	public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, data, "sukses");
    }

    public static <T> ApiResponse<T> failed(HttpStatus httpStatus, String message) {
        return new ApiResponse<>(httpStatus.value(), null, "failed: " + message);
    }
    
    public static ApiResponse<Map<String, Object>> buildRekeningResponse(Rekening rekening) {
    	
        Map<String, Object> rekeningData = new HashMap<>();
        rekeningData.put("created_date", rekening.getCreatedDate());
        rekeningData.put("updated_date", rekening.getUpdateDate());
        rekeningData.put("deleted_date", rekening.getDeletedDate());
        rekeningData.put("id", rekening.getId());
        rekeningData.put("nama", rekening.getNama());
        rekeningData.put("jenis", rekening.getJenis());
        rekeningData.put("rekening", rekening.getRekening());
        rekeningData.put("alamat", rekening.getKaryawan().getAlamat());

        Map<String, Object> karyawanData = new HashMap<>();
        karyawanData.put("id", rekening.getKaryawan().getId());
        karyawanData.put("nama", rekening.getKaryawan().getNama());

        rekeningData.put("karyawan", karyawanData);

        return ApiResponse.success(rekeningData);
    }
}
