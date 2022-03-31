package com.indianexpress.application.shared.utils;//package com.indianexpress.search.application.shared.utils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.HashMap;
//
//public class StdPageResponse {
//
//    private static final Logger logger = LoggerFactory.getLogger(StdPageResponse.class);
//
//    private Page page;
//    private HttpServletRequest request;
//    private String siteDomain;
//
//    public StdPageResponse(Page page, HttpServletRequest request){
//        this.page = page;
//        this.request = request;
//        this.siteDomain = (String) SpringContextProvider.getEnvironmentValue("SITE_DOMAIN");
//    }
//
//    public String getEncodedUrlParams(HashMap paramsMap){
//
//        String encodedParamsString = "";
//        for (Object key:paramsMap.keySet()){
//            encodedParamsString = encodedParamsString + key + "=" +paramsMap.get(key).toString() + "&";
//        }
//        // Replace the last occurrence of & in the encoded params.
//        if (encodedParamsString.length() > 0){
//            encodedParamsString = encodedParamsString.substring(0,encodedParamsString.length()-1);
//        }
//        return encodedParamsString;
//    }
//
//    public HashMap<?,?> prepareResponse() {
//        String urlPath;
//        String[] splitKVs = request.getQueryString()!=null?request.getQueryString().split("&"): new String[]{};
//
//        HashMap paramsMap = new HashMap();
//        HashMap<Object,Object> responseMap = new HashMap<Object,Object>();
//
//        for (String kv:splitKVs){
//            String[] splitValue = kv.split("=",-1);
//            if (splitValue.length < 2){
//                continue;
//            }
//            paramsMap.put(splitValue[0],String.join("=",Arrays.copyOfRange(splitValue,1,splitValue.length)));
//        }
//
//        responseMap.put("count",page.getTotalElements());
//        int pageNo = page.getNumber();
//
//        // Insert next url in response body, if exists.
//        paramsMap.put("page",pageNo+2);
//        responseMap.put("next",page.hasNext()? this.siteDomain + request.getRequestURI() + "?"+ getEncodedUrlParams(paramsMap):null);
//
//        // Insert previous url in response body, if exists.
//        paramsMap.put("page",pageNo);
//        responseMap.put("previous",page.hasPrevious()?this.siteDomain + request.getRequestURI() + "?"+ getEncodedUrlParams(paramsMap):null);
//
//        responseMap.put("results",page.getContent());
//
//        return responseMap;
//    }
//}
