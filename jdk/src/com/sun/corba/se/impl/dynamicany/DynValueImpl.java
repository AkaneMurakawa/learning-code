/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.dynamicany;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.Any;
import org.omg.DynamicAny.*;

import com.sun.corba.se.spi.orb.ORB ;

public class DynValueImpl extends DynValueCommonImpl implements DynValue
{
    //
    // Constructors
    //

    private DynValueImpl() {
        this(null, (Any)null, false);
    }

    protected DynValueImpl(ORB orb, Any any, boolean copyValue) {
        super(orb, any, copyValue);
    }

    protected DynValueImpl(ORB orb, TypeCode typeCode) {
        super(orb, typeCode);
    }
}
