package IDS.IDPLV4.Utility;

import org.springframework.http.HttpStatus;

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
        return new ApiResponse<>(200, data, "success");
    }

    public static <T> ApiResponse<T> failed(HttpStatus httpStatus, String message) {
        return new ApiResponse<>(httpStatus.value(), null, "failed: " + message);
    }
}
