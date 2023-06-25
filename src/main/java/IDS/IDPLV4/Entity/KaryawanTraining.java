package IDS.IDPLV4.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime createdDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "tanggal")
    private LocalDate tanggal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_karyawan")
    private Karyawan karyawan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_training")
    private Training training;
    
    @PrePersist
    protected void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }
}
