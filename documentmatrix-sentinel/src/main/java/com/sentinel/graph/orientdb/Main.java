package com.sentinel.graph.orientdb;

public class Main
{
    private static int index;


    public static void main( String[] args )
    {
        ElementExample elementExample = new ElementExample( getNextDbUrl() );
        elementExample.run();

        TypedElementExample typedElementExample = new TypedElementExample( getNextDbUrl() );
        typedElementExample.run();

        OrientTypedElementExample oTypedElementExample = new OrientTypedElementExample( getNextDbUrl() );
        oTypedElementExample.run();
    }


    private static String getNextDbUrl()
    {
        return "memory:db" + index++;
    }
}
