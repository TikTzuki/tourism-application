
package com.tourism.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Tour
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tour {

	Long id;
	Long typeId;
	String name;
	String description;
	String status;
	String image;
	protected Type type ;
	protected TourCost cost;
	List<TouristGroup> touristGroups ;
	List<Location> locations;
	List<TourCost> tourCosts;
	Location location;
	List<Long> idLocation;
}
