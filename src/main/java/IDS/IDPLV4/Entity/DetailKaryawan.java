package IDS.IDPLV4.Entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter@Getter
@Table(name = "detail_karyawan")
public class DetailKaryawan {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "nik")
    private String nik;

    @Column(name = "npwp")
    private String npwp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "karyawan_id")
    private Karyawan karyawan;
}
