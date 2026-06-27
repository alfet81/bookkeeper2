package com.bookkeeper.category.view.mapper;

import com.bookkeeper.category.model.Category;
import com.bookkeeper.category.view.model.CategoryTreeViewItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-12T03:38:43-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Amazon.com Inc.)"
)
@Component
public class CategoryTreeViewItemMapperImpl implements CategoryTreeViewItemMapper {

    @Override
    public CategoryTreeViewItem toCategoryTreeViewItem(Category category, CategoryTreeViewItemCyclicGraphContext context) {
        CategoryTreeViewItem target = context.getMappedInstance( category, CategoryTreeViewItem.class );
        if ( target != null ) {
            return target;
        }

        if ( category == null ) {
            return null;
        }

        Category value = null;
        String name = null;

        value = category;
        name = category.getName();

        CategoryTreeViewItem categoryTreeViewItem = new CategoryTreeViewItem( name, value );

        categoryTreeViewItem.setParent( toCategoryTreeViewItem( category.getParent(), context ) );

        context.storeMappedInstance( category, categoryTreeViewItem );

        return categoryTreeViewItem;
    }

    @Override
    public List<CategoryTreeViewItem> toCategoryTreeViewItems(List<Category> categories, CategoryTreeViewItemCyclicGraphContext context) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryTreeViewItem> list = new ArrayList<CategoryTreeViewItem>( categories.size() );
        for ( Category category : categories ) {
            list.add( toCategoryTreeViewItem( category, context ) );
        }

        return list;
    }
}
