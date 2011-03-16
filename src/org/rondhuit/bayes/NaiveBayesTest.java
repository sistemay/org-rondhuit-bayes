package org.rondhuit.bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NaiveBayesTest {
  
  private static Random r = new Random(System.currentTimeMillis());
  private static final String[] EMPLOYEES = {
    "general",       // general
    "engineering",   // technical
    "sales",         // sales
    "marketing"      // marketing
  };
  private static final String[] QUERY_TERMS = {
    "product",          // general
    "technology",       // technical
    "presentation",     // sales
    "research"          // marketing
  };
  private static final int MAX_QUERIES = 16;
  private static final String[] URLS = {
    "company.html",
    "technology.html",
    "products.html",
    "services.html"
  };
  private static final int[][] PERCENTS = {
    {40, 60, 80, 100},
    {20, 60, 80, 100},
    {20, 40, 80, 100},
    {20, 40, 60, 100}
  };

  public static void main(String[] args) throws Exception {
    final int MAX_CLICKS = 10000;
    List<UserClick> clicks = new ArrayList<UserClick>();
    for( int i = 0; i < MAX_CLICKS; i++){
      clicks.add(getRandomUserClick());
    }
    TrainingSet tSet = new TrainingSet(clicks.toArray(new UserClick[MAX_CLICKS]));
    NaiveBayes naiveBayes = new NaiveBayes("Naive Bayes", tSet);
    naiveBayes.trainOnAttribute("UserName");
    naiveBayes.trainOnAttribute("QueryTerm_1");
    naiveBayes.trainOnAttribute("QueryTerm_2");
    naiveBayes.train();
    
    // test the result of the training
    for(int uid = 0; uid < EMPLOYEES.length; uid++){
      System.out.println(EMPLOYEES[uid]);
      for(int qid = 0; qid < MAX_QUERIES; qid++){
        System.out.println("\t" + getQuery(qid));
        for(int urlId = 0; urlId < URLS.length; urlId++){
          Concept c = naiveBayes.classify(getUserClick(EMPLOYEES[uid], getQuery(qid), URLS[urlId]));
          System.out.println("\t\t" + URLS[urlId] + " => " + c);
        }
      }
    }
  }
  
  private static UserClick getRandomUserClick(){
    int uid = getRandomUser();
    String user = EMPLOYEES[uid];
    String query = getRandomQuery();
    String url = getRandomUrl(uid);
    return getUserClick(user, query, url);
  }

  private static UserClick getUserClick(String uid, String query, String url){
    UserQuery uq = new UserQuery(uid, query);
    return new UserClick(uq, url);
  }
  
  private static int getRandomUser(){
    return r.nextInt(EMPLOYEES.length);
  }
  
  private static String getRandomQuery(){
    return getQuery(r.nextInt(MAX_QUERIES));
  }
  
  private static String getQuery(int rq){
    if(rq < 4){
      return QUERY_TERMS[rq];
    }
    else if(rq < 7){
      return QUERY_TERMS[0] + " " + QUERY_TERMS[rq - 3];
    }
    else if(rq < 10){
      return QUERY_TERMS[1] + " " + QUERY_TERMS[rq == 7 ? 0 : rq - 6];
    }
    else if(rq < 13){
      return QUERY_TERMS[2] + " " + QUERY_TERMS[rq < 12 ? rq - 10 : 3];
    }
    else{
      return QUERY_TERMS[3] + " " + QUERY_TERMS[rq - 13];
    }
  }
  
  private static String getRandomUrl(int uid){
    int ru = r.nextInt(100);
    int[] percents = PERCENTS[uid];
    for(int i = 0; i < percents.length; i++){
      if(ru < percents[i])
        return URLS[i];
    }
    // never get here
    return URLS[percents.length - 1];
  }
}
