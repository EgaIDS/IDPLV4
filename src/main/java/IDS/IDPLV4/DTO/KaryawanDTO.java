package IDS.IDPLV4.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class KaryawanDTO {

	private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private LocalDateTime deleted_date;
    private Long id;
    private String nama;
    private LocalDate dob;
    private String status;
    private String alamat;
    private DetailKaryawanDTO detailKaryawan;
}
