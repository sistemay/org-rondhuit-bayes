/*
 * This program is distributed under LGPL.
 * 
 * Source:
 * http://code.google.com/p/yooreeka/source/browse/tags/Book-final-code/tags/Final+code+of+the+book/src/iweb2/ch2/clicks/UserClick.java
 */
package org.rondhuit.bayes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class captures a user click.
 * 
 * @author babis
 *
 */
public class UserClick implements Instance {

  Concept concept;          // url
  Attribute[] attributes;   // uid and each query term
	
	public UserClick(String uid, String query, String url){
	  this(new UserQuery(uid, query), url);
	}
	
	public UserClick(UserQuery uq, String url) {
    concept = new BaseConcept(url);
    attributes = new StringAttribute[uq.getQueryTerms().length+1];
    attributes[0] = new StringAttribute("UserName",uq.getUid());
    int j = 0;
    for (String s : uq.getQueryTerms()) {
      attributes[j] = new StringAttribute("QueryTerm_"+j,s);
      j++;
    }
	}
	
  public static UserClick[] load(BufferedReader bR) throws IOException {
    List<UserClick> userClicks = new ArrayList<UserClick>();
    String line;
    boolean hasMoreLines = true;
    while (hasMoreLines) {
      line=bR.readLine();
      if (line == null) {
        hasMoreLines = false;
      } else {
        String[] data = line.split(",");
        assert(data.length == 3);
        UserClick userClick = new UserClick(data[0], data[1], data[2]);
        //userClick.print();
        userClicks.add(userClick);
      }
    }
    return userClicks.toArray(new UserClick[userClicks.size()]);
	}

  /**
   * Pretty print the information for this Instance
   */
  public void print() {
    if (attributes != null) {
      for (Attribute a : attributes) {
        if ( a == null || a.getName() == null) {
          System.out.print(" -  <NULL ATTRIBUTE> ");
        } else {
          if (a.getValue() == null) {
            System.out.print(" -  <NULL ATTRIBUTE VALUE> ");
          } else {
            System.out.print(" -  "+a.getName()+" = "+a.getValue());
          }
        }
      }
    }
    System.out.println(" -->  "+getConcept().getName());
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((concept == null) ? 0 : concept.hashCode());
    for(Attribute a : attributes){
      result = prime * result + a.hashCode();
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof UserClick))
      return false;
    final UserClick other = (UserClick) obj;
    if (concept == null) {
      if (other.concept != null)
        return false;
    } else if (!concept.equals(other.concept))
      return false;
    if(attributes.length != other.attributes.length)
      return false;
    // sequence must be same if they are equal
    for(int i = 0; i < attributes.length; i++){
      if(attributes[i] != other.attributes[i])
        return false;
    }
    return true;
  }

  public Attribute[] getAtrributes() {
    return attributes;
  }

  public Concept getConcept() {
    return concept;
  }
}
