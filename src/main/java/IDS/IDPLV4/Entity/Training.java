package IDS.IDPLV4.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Setter@Getter
@Table(name = "training")
public class Training {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "pengajar")
    private String pengajar;

    @Column(name = "tema")
    private String tema;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KaryawanTraining> karyawanTrainingList;
    
    @PrePersist
    protected void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }
}
