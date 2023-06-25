package IDS.IDPLV4.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class RekeningDTO {

	private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;
    private Long id;
    private String nama;
    private String jenis;
    private String rekening;
    private String alamat;
    private KaryawanRekeningDTO karyawan;
}
