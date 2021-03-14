
package com.tourism.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourCost {
	Long id;
	Long tourId;
	Double price;
	Date PriceFromTime;
	Date PriceToTime;
}


