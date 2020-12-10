package com.enioka.jqm.osgi.jdbc;

public class NonUniqueResultException extends DatabaseException
{
    private static final long serialVersionUID = -6629810255619548599L;

    public NonUniqueResultException(String e)
    {
        super(e);
    }

}
