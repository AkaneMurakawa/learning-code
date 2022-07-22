/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.encoding;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.nio.ByteBuffer;

import org.omg.CORBA_2_3.portable.InputStream;

import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import com.sun.corba.se.impl.corba.TypeCodeImpl;
import com.sun.corba.se.spi.orb.ORB;

import sun.corba.EncapsInputStreamFactory;

public class TypeCodeInputStream extends EncapsInputStream implements TypeCodeReader
{
    private Map typeMap = null;
    private InputStream enclosure = null;
    private boolean isEncapsulation = false;

    public TypeCodeInputStream(org.omg.CORBA.ORB orb, byte[] data, int size) {
        super(orb, data, size);
    }

    public TypeCodeInputStream(org.omg.CORBA.ORB orb,
                               byte[] data,
                               int size,
                               boolean littleEndian,
                               GIOPVersion version) {
        super(orb, data, size, littleEndian, version);
    }

    public TypeCodeInputStream(org.omg.CORBA.ORB orb,
                               ByteBuffer byteBuffer,
                               int size,
                               boolean littleEndian,
                               GIOPVersion version) {
        super(orb, byteBuffer, size, littleEndian, version);
    }

    public void addTypeCodeAtPosition(TypeCodeImpl tc, int position) {
        if (typeMap == null) {
            //if (TypeCodeImpl.debug) System.out.println("Creating typeMap");
            typeMap = new HashMap(16);
        }
        //if (TypeCodeImpl.debug) System.out.println(this + " adding tc " + tc + " at position " + position);
        typeMap.put(new Integer(position), tc);
    }

    public TypeCodeImpl getTypeCodeAtPosition(int position) {
        if (typeMap == null)
            return null;
        //if (TypeCodeImpl.debug) {
            //System.out.println("Getting tc " + (TypeCode)typeMap.get(new Integer(position)) +
                               //" at position " + position);
        //}
        return (TypeCodeImpl)typeMap.get(new Integer(position));
    }

    public void setEnclosingInputStream(InputStream enclosure) {
        this.enclosure = enclosure;
    }

    public TypeCodeReader getTopLevelStream() {
        if (enclosure == null)
            return this;
        if (enclosure instanceof TypeCodeReader)
            return ((TypeCodeReader)enclosure).getTopLevelStream();
        return this;
    }

    public int getTopLevelPosition() {
        if (enclosure != null && enclosure instanceof TypeCodeReader) {
            // The enclosed stream has to consider if the enclosing stream
            // had to read the enclosed stream completely when creating it.
            // This is why the size of the enclosed stream needs to be substracted.
            int topPos = ((TypeCodeReader)enclosure).getTopLevelPosition();
            // Substract getBufferLength from the parents pos because it read this stream
            // from its own when creating it
            int pos = topPos - getBufferLength() + getPosition();
            //if (TypeCodeImpl.debug) {
                //System.out.println("TypeCodeInputStream.getTopLevelPosition using getTopLevelPosition " + topPos +
                    //(isEncapsulation ? " - encaps length 4" : "") +
                    //" - getBufferLength() " + getBufferLength() +
                    //" + getPosition() " + getPosition() + " = " + pos);
            //}
            return pos;
        }
        //if (TypeCodeImpl.debug) {
            //System.out.println("TypeCodeInputStream.getTopLevelPosition returning getPosition() = " +
                               //getPosition() + " because enclosure is " + enclosure);
        //}
        return getPosition();
    }

    public static TypeCodeInputStream readEncapsulation(InputStream is, org.omg.CORBA.ORB _orb) {
        // _REVISIT_ Would be nice if we didn't have to copy the buffer!
        TypeCodeInputStream encap;

        int encapLength = is.read_long();

        // read off part of the buffer corresponding to the encapsulation
        byte[] encapBuffer = new byte[encapLength];
        is.read_octet_array(encapBuffer, 0, encapBuffer.length);

        // create an encapsulation using the marshal buffer
        if (is instanceof CDRInputStream) {
            encap = EncapsInputStreamFactory.newTypeCodeInputStream((ORB) _orb,
                    encapBuffer, encapBuffer.length,
                    ((CDRInputStream) is).isLittleEndian(),
                    ((CDRInputStream) is).getGIOPVersion());
        } else {
            encap = EncapsInputStreamFactory.newTypeCodeInputStream((ORB) _orb,
                    encapBuffer, encapBuffer.length);
        }
        encap.setEnclosingInputStream(is);
        encap.makeEncapsulation();
        //if (TypeCodeImpl.debug) {
            //System.out.println("Created TypeCodeInputStream " + encap + " with parent " + is);
            //encap.printBuffer();
        //}
        return encap;
    }

    protected void makeEncapsulation() {
        // first entry in an encapsulation is the endianess
        consumeEndian();
        isEncapsulation = true;
    }

    public void printTypeMap() {
        System.out.println("typeMap = {");
        Iterator i = typeMap.keySet().iterator();
        while (i.hasNext()) {
            Integer pos = (Integer)i.next();
            TypeCodeImpl tci = (TypeCodeImpl)typeMap.get(pos);
            System.out.println("  key = " + pos.intValue() + ", value = " + tci.description());
        }
        System.out.println("}");
    }
}
