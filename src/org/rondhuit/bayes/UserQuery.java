/*
 * This program is distributed under LGPL.
 * 
 * Source:
 * http://code.google.com/p/yooreeka/source/browse/tags/Book-final-code/tags/Final%20code%20of%20the%20book/src/iweb2/ch2/clicks/UserQuery.java
 */
package org.rondhuit.bayes;

import java.util.Arrays;

/**
 * This is a class that encapsulates a personalized query
 * 
 * @author babis
 *
 */
public class UserQuery {

	private String uid;
	private String query;
	private String[] queryTerms;
	
	public UserQuery(String uid, String q){
		setUid(uid);
		setQuery(q);
		queryTerms = parseQuery();
	}

	/**
	 * Subclass may want to override this method.
	 * The default implementation outputs tokens by splitting each spaces.
	 * 
	 * @return devided token strings
	 */
	protected String[] parseQuery(){
	  return query.split("\\s+");
	}
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getName() {
		return UserQuery.class.getCanonicalName();
	}

	public UserQuery getValue() {
		
		return this;
	}

	/**
	 * @return the queryTerms
	 */
	public String[] getQueryTerms() {
		return queryTerms;
	}

  @Override
  public int hashCode() {
    final int prime = 17;
    int result = 1;
    result = prime * result + ((query == null) ? 0 : query.hashCode());
    result = prime * result + Arrays.hashCode(queryTerms);
    result = prime * result + ((uid == null) ? 0 : uid.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof UserQuery))
      return false;
    final UserQuery other = (UserQuery) obj;
    if (query == null) {
      if (other.query != null)
          return false;
    } else if (!query.equals(other.query))
      return false;
    if (uid == null) {
      if (other.uid != null)
        return false;
    } else if (!uid.equals(other.uid))
      return false;
    return true;
  }
}
