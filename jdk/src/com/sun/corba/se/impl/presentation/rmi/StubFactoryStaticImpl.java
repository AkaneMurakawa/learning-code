/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.corba.se.impl.presentation.rmi;

public class StubFactoryStaticImpl extends StubFactoryBase
{
    private Class stubClass ;

    public StubFactoryStaticImpl(Class cls)
    {
        super( null ) ;
        this.stubClass = cls;
    }

    public org.omg.CORBA.Object makeStub()
    {
        org.omg.CORBA.Object stub = null;
        try {
            stub = (org.omg.CORBA.Object) stubClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return stub ;
    }
}
