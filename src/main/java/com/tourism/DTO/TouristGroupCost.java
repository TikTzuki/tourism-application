package com.tourism.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * Hotel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TouristGroupCost {

  protected Long id;
  Long touristGroupId;
  Double totalPrice;
  List<TouristGroupCostItem> description;
  TouristGroup touristGroups;
}
