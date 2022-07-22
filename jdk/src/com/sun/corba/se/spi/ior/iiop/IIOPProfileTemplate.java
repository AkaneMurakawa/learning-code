/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.TaggedProfileTemplate ;

/**
 * IIOPProfileTemplate represents the parts of an IIOPProfile that are independent
 * of the object identifier.  It is a container of tagged components.
 */
public interface IIOPProfileTemplate extends TaggedProfileTemplate
{
    /** Return the GIOP version of this profile.
    */
    public GIOPVersion getGIOPVersion() ;

    /** Return the IIOP address from the IIOP profile.  This is called the
    * primary address here since other addresses may be contained in
    * components.
    */
    public IIOPAddress getPrimaryAddress()  ;
}
