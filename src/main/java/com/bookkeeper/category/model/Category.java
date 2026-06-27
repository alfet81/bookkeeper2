package com.bookkeeper.category.model;

import com.bookkeeper.common.model.types.EntryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  private Long id;
  private String name;
  private EntryType entryType;
  private BigDecimal defaultAmount;
  private CategoryIcon icon;

  @ToString.Exclude
  private Category parent;
}
