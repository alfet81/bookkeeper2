package com.bookkeeper.csv;

import static com.bookkeeper.type.CsvRecordColumn.AMOUNT;
import static com.bookkeeper.type.CsvRecordColumn.CATEGORY;
import static com.bookkeeper.type.CsvRecordColumn.DATE;
import static com.bookkeeper.type.CsvRecordColumn.NOTES;
import static com.bookkeeper.utils.CsvUtils.getColumnValue;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

import com.bookkeeper.domain.account.Account;
import com.bookkeeper.domain.category.Category;
import com.bookkeeper.domain.category.CategoryService;
import com.bookkeeper.domain.entry.Entry;
import com.bookkeeper.type.CsvRecordStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.function.Function;

@Data
@Scope(SCOPE_PROTOTYPE)
@ToString(of = {"date", "amount", "category", "notes", "status", "errors", "entry"})
public class CsvRecordWrapper {

  @NotNull(message = "Missing date")
  private String date;

  @NotNull(message = "Missing amount")
  @Digits(integer = 9, fraction = 2, message = "Invalid amount")
  private String amount;

  @NotNull(message = "Missing category name")
  @Size(min = 1, max = 100, message = "Category name exceeds 100 chars")
  private String category;

  @Size(max = 255, message = "Notes exceeds 255 chars")
  private String notes;

  private CsvRecordStatus status;

  private String errors;

  private Entry entry;

  @Autowired
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Function<CsvRecordWrapper, CsvEntryBuilder> entryBuilderFactory;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private CsvEntryBuilder csvEntryBuilder;

  @Autowired
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private CategoryService categoryService;

  @SuppressWarnings("ConstantConditions")
  public CsvRecordWrapper(CSVRecord csvRecord) {
    date = getColumnValue(csvRecord, DATE);
    amount = getColumnValue(csvRecord, AMOUNT);
    category = getColumnValue(csvRecord, CATEGORY);
    notes = getColumnValue(csvRecord, NOTES);
  }

  @PostConstruct
  protected void init() {
    csvEntryBuilder = getCsvEntryBuilder();
    csvEntryBuilder.setCategories(getCategories());
  }

  public void process() {
    csvEntryBuilder.build();
  }

  private CsvEntryBuilder getCsvEntryBuilder() {
    return entryBuilderFactory.apply(this);
  }

  private Map<String, Category> getCategories() {
    return categoryService.findAll().stream()
        .filter(Category::isLeaf)
        .collect(toMap(c -> c.getName().toLowerCase(), c -> c, (c1, c2) -> c1));
  }
}
