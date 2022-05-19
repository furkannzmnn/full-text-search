package com.example.fulltextsearch.common;

import com.example.fulltextsearch.dto.SourceBuilderRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public final class QueryUtil {

    private QueryUtil() {
        throw new AssertionError();
    }

    public static String[] getTerms(String query) {
        return query.split("\\s+");
    }

    public static SearchSourceBuilder buildQuery(SourceBuilderRequest request) {
        SortOrder sortOrder = request.getSort().values().stream().findFirst().orElse(SortOrder.ASC);
        final String sortField = request.getSort().keySet().iterator().next();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(request.getSourceBuilder());
        searchSourceBuilder.size(request.getSize());
        searchSourceBuilder.from(request.getFrom());
        searchSourceBuilder.sort(sortField, sortOrder);
        return searchSourceBuilder;
    }

    public static String[] getTerms(String query, String[] stopWords) {
        String[] terms = getTerms(query);
        String[] result = new String[terms.length];
        int j = 0;
        for (String term : terms) {
            if (!isStopWord(term, stopWords)) {
                result[j++] = term;
            }
        }
        return result;
    }

    public static boolean isStopWord(String term, String[] stopWords) {
        for (String stopWord : stopWords) {
            if (term.equals(stopWord)) {
                return true;
            }
        }
        return false;
    }
}
