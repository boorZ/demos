package com.example.noaskqaes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.noaskqaes.utils.RestTemplateUtils;
import com.example.noaskqaes.vo.dto.QaDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/29 10:40
 */
public class ISearchService {
    static String docName = "docName";
    static String indexName = "se_qa";

    public static List<String> synonym(String searchValue) {
        String read = IFileService.readSynonym();
        String[] synonyms = read.split("\n");
        return Arrays.stream(synonyms).filter(f -> {
            String[] sonSynonyms = f.split(",");
            for (String sonSynonym : sonSynonyms) {
                if (searchValue.contains(sonSynonym)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    public static String mappingSynonym(String searchValue) {
        String read = IFileService.readMappingSynonym();
        if ("".equals(read)) {
            return searchValue;
        }
        String[] synonyms = read.split("\n");
        for (String synonym : synonyms) {
            String[] split = synonym.split("=>");
            String k = split[0];
            String v = split[1];
            String[] ks = k.split(",");
            for (String s : ks) {
                if (searchValue.contains(s)) {
                    searchValue = searchValue.replaceAll(s, v);
                    break;
                }
            }
        }
        return searchValue;
    }

    public static List<String> coreWord(String searchValue) {
        String read = IFileService.readCoreWord();
        String[] coreWords = read.split("\n");
        return Arrays.stream(coreWords).filter(searchValue::contains).map(m -> m += "^2").collect(Collectors.toList());
//        return Arrays.stream(coreWords).filter(searchValue::contains).collect(Collectors.toList());
    }

    public static RestHighLevelClient client() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("xxx", "xxx"));
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("xxx", 9201, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder -> {
                            httpClientBuilder.disableAuthCaching();
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }));
    }

    public static SearchRequest dsl(String searchValue, Consumer<SearchSourceBuilder> dslConsumer) {
        SearchSourceBuilder dsl = new SearchSourceBuilder();
        dsl.query(dsl(searchValue));
        dslConsumer.accept(dsl);
        System.out.println("查询语句:\n" + dsl.toString().replaceAll("\\{\"size\":.,", "{\"_source\": \"docName\",") + "\n");
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(dsl);
        return searchRequest;
    }
    private static QueryBuilder dsl(String searchValue) {
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        bool.must(QueryBuilders.matchQuery(docName, searchValue));
        return bool;
    }
    private static QueryBuilder dsls(String searchValue) {
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        // 关键词
        List<String> intervals = IFileService.readInterval();
        if (intervals.size() > 0) {
            List<String> validIntervals = intervals.stream().filter(searchValue::contains).collect(Collectors.toList());
            for (String interval : validIntervals) {
                IntervalsSourceProvider.Match match = new IntervalsSourceProvider.Match(interval, 3, true, null, null);
                IntervalQueryBuilder intervalQueryBuilder = new IntervalQueryBuilder(docName, match).boost(0);
                bool.must(intervalQueryBuilder);
            }
        }

        // 映射同义词
        searchValue = mappingSynonym(searchValue);
        // 同义词
        List<String> synonyms = synonym(searchValue);
        if (synonyms != null && synonyms.size() > 0) {
            String str = StringUtils.join(synonyms, " ").replaceAll(",", " ");
            bool.should(QueryBuilders.queryStringQuery(str).defaultField(docName));
        }
        // 专业（核心）词
        List<String> coreWords = coreWord(searchValue);
        if (coreWords != null && coreWords.size() > 0) {
            coreWords = coreWords.stream().distinct().collect(Collectors.toList());
            bool.should(QueryBuilders.queryStringQuery(StringUtils.join(coreWords, " ")).defaultField(docName));
        }
        bool.must(QueryBuilders.matchQuery(docName, searchValue));
        return bool;
    }

    public static List<QaDTO> newSearch(String searchValue, int size) throws IOException {
        RestHighLevelClient client = client();
        // 搜索条件
        SearchRequest searchRequest = dsl(searchValue, dsl -> {
            dsl.size(size);
        });

        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = search.getHits();
        List<QaDTO> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            JSONObject jsonObject = new JSONObject(sourceAsMap);
            QaDTO qaDTO = jsonObject.toJavaObject(QaDTO.class);
            list.add(qaDTO);
        }
        client.close();
        return list;
    }

    public static String searchList(String search, String judgeId, String judgeName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("search", search);
        String forObject = RestTemplateUtils.getForObject("https://sw.noask-ai.com/portal/qa-new/hotline/es/list", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONArray results = jsonObject.getJSONArray("result");
        if (results == null || results.size() == 0) {
//            System.out.println(String.format("search:%s，无数据", search));
            return "";
        }
        Object result = results.get(0);
        JSONObject resultJson = JSONObject.parseObject(result.toString());
        String docId = resultJson.getString("docId");
        boolean isDocId = docId.equals(judgeId);
//        System.out.println(String.format("search:%s, docId:%s", search, isDocId));

        int size = results.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String s = ((size - 1) < i ? "" : docName(results.get(i)));
            sb.append("\t").append(s);
        }
        return String.format("%s%s\t%s\t%s\t%s", search, sb.toString(), judgeName, (isDocId ? 0 : 1), (isDocId ? 1 : 0));
    }

    protected static String docName(Object result) {
        JSONObject resultJson = JSONObject.parseObject(result.toString());
        return resultJson.getString("docName");
    }
}
