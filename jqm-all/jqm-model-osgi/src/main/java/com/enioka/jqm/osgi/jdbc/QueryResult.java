package com.enioka.jqm.osgi.jdbc;

public class QueryResult
{
    public int nbUpdated = 0;
    public Integer generatedKey = null;

    public Integer getGeneratedId()
    {
        return generatedKey;
    }

}
