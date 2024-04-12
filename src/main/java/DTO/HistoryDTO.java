package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryDTO {
	private int id;				// 번호
	private double lat;			// X좌표
	private double lnt;         // y좌표
	private String searchDttm;  // 조회일자
}
