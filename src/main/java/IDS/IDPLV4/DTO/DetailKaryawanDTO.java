package IDS.IDPLV4.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class DetailKaryawanDTO {
	private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private LocalDateTime deleted_date;
    private Long id;
    private String nik;
    private String npwp;
}
