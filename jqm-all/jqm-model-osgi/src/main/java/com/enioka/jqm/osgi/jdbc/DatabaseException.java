package com.enioka.jqm.osgi.jdbc;

public class DatabaseException extends RuntimeException
{
    private static final long serialVersionUID = -3989188748702776603L;

    public DatabaseException(Exception e)
    {
        super(e);
    }

    public DatabaseException(String e)
    {
        super(e);
    }

    public DatabaseException(String e, Exception ex)
    {
        super(e, ex);
    }
}
