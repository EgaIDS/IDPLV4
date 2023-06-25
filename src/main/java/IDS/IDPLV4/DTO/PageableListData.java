package IDS.IDPLV4.DTO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import IDS.IDPLV4.Entity.Karyawan;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class PageableListData<T> {

	private List<T> content;
    private Pageable pageable;
    private boolean last;
    private long totalElements;
    private int totalPages;
    private int size;
    private int number;
    private Sort sort;
    private boolean first;
    private int numberOfElements;
    private boolean empty;
}
