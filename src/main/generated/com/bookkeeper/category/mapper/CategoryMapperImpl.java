package com.bookkeeper.category.mapper;

import com.bookkeeper.category.model.Category;
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
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public com.bookkeeper.category.jpa.Category toEntity(Category category) {
        if ( category == null ) {
            return null;
        }

        com.bookkeeper.category.jpa.Category.CategoryBuilder<?, ?> category1 = com.bookkeeper.category.jpa.Category.builder();

        category1.id( category.getId() );
        category1.name( category.getName() );
        category1.entryType( category.getEntryType() );
        category1.defaultAmount( category.getDefaultAmount() );

        return category1.build();
    }

    @Override
    public List<com.bookkeeper.category.jpa.Category> toEntities(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<com.bookkeeper.category.jpa.Category> list = new ArrayList<com.bookkeeper.category.jpa.Category>( categories.size() );
        for ( Category category : categories ) {
            list.add( toEntity( category ) );
        }

        return list;
    }

    @Override
    public Category toModel(com.bookkeeper.category.jpa.Category categoryEntity, CategoryCyclicGraphContext context) {
        Category target = context.getMappedInstance( categoryEntity, Category.class );
        if ( target != null ) {
            return target;
        }

        if ( categoryEntity == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( categoryEntity.getId() );
        category.name( categoryEntity.getName() );
        category.entryType( categoryEntity.getEntryType() );
        category.defaultAmount( categoryEntity.getDefaultAmount() );
        category.parent( toModel( categoryEntity.getParent(), context ) );

        Category categoryResult = category.build();

        context.storeMappedInstance( categoryEntity, categoryResult );

        return categoryResult;
    }

    @Override
    public List<Category> toModel(List<com.bookkeeper.category.jpa.Category> categoryEntities, CategoryCyclicGraphContext context) {
        if ( categoryEntities == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( categoryEntities.size() );
        for ( com.bookkeeper.category.jpa.Category category : categoryEntities ) {
            list.add( toModel( category, context ) );
        }

        return list;
    }
}
