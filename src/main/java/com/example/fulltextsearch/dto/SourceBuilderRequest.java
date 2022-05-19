package com.example.fulltextsearch.dto;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Map;

public final class SourceBuilderRequest {

    private final QueryBuilder sourceBuilder;
    private final Map<String, SortOrder> sort;
    private final int size;
    private final int from;

    public SourceBuilderRequest(Builder builder) {
        this.sourceBuilder = builder.sourceBuilder;
        this.sort = builder.sort;
        this.size = builder.size;
        this.from = builder.from;
    }

    public QueryBuilder getSourceBuilder() {
        return sourceBuilder;
    }

    public Map<String, SortOrder> getSort() {
        return sort;
    }

    public int getSize() {
        return size;
    }

    public int getFrom() {
        return from;
    }


    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private QueryBuilder sourceBuilder;
        private Map<String, SortOrder> sort;
        private int size;
        private int from;

        private Builder() {
        }


        public Builder sourceBuilder(QueryBuilder sourceBuilder) {
            this.sourceBuilder = sourceBuilder;
            return this;
        }

        public Builder sort(Map<String, SortOrder> sort) {
            this.sort = sort;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder from(int from) {
            this.from = from;
            return this;
        }

        public SourceBuilderRequest build() {
            return new SourceBuilderRequest(this);
        }
    }
}
