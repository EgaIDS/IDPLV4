package IDS.IDPLV4.Entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter@Getter
@Table(name = "karyawan_training")
public class KaryawanTraining {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "tanggal")
    private LocalDate tanggal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_karyawan")
    private Karyawan karyawan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_training")
    private Training training;
}
