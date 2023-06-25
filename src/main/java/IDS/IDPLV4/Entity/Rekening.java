package IDS.IDPLV4.Entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Setter@Getter
@Table(name = "rekening")
public class Rekening {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "jenis")
    private String jenis;

    @Column(name = "nama")
    private String nama;

    @Column(name = "rekening")
    private String rekening;

    // Many-to-One relationship with Karyawan
    @ManyToOne
    @JoinColumn(name = "karyawan_id")
    private Karyawan karyawan;
    
    @PrePersist
    protected void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }
    
    
}
