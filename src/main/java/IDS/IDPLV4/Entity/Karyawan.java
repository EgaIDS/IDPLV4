package IDS.IDPLV4.Entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter@Getter
@Table(name = "karyawan")
public class Karyawan {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "nama")
    private String nama;

    @Column(name = "status")
    private String status;

    @OneToOne(mappedBy = "karyawan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DetailKaryawan detailKaryawan;

    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KaryawanTraining> karyawanTrainings;

    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rekening> rekenings;
    
    @PrePersist
    protected void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }
    
}
