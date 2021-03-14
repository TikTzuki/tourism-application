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
 * TouristGroup
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TouristGroup {
	protected Long id;
	protected Long tourId;
	protected String name;
	protected Date depatureDate;
	protected Date endDate;
	protected String status;
	protected Double revenue;
	protected List<Customer> customers;
	protected Tour tour;
	protected List<TourPosition> tourPositions;
	protected List<TouristGroupCost> touristGroupCosts;

	public static void main(String[] args) {
	}
}
