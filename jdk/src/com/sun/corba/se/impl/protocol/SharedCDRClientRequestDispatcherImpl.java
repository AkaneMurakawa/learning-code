/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 */

package com.sun.corba.se.impl.protocol;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.omg.CORBA.portable.ApplicationException;

import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.encoding.OutputObject;

import com.sun.corba.se.spi.orb.ORB;
import com.sun.corba.se.spi.protocol.CorbaMessageMediator;

import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import com.sun.corba.se.impl.encoding.CDRInputObject;
import com.sun.corba.se.impl.encoding.CDROutputObject;
import com.sun.corba.se.impl.orbutil.ORBUtility;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/**
 * ClientDelegate is the RMI client-side subcontract or representation
 * It implements RMI delegate as well as our internal ClientRequestDispatcher
 * interface.
 */
public class SharedCDRClientRequestDispatcherImpl
    extends
        CorbaClientRequestDispatcherImpl
{
    // REVISIT:
    // Rather than have separate CDR subcontract,
    // use same CorbaClientRequestDispatcherImpl but have
    // different MessageMediator finishSendingRequest and waitForResponse
    // handle what is done below.
    // Benefit: then in ContactInfo no need to do a direct new
    // of subcontract - does not complicate subcontract registry.

    public InputObject marshalingComplete(java.lang.Object self,
                                          OutputObject outputObject)
        throws
            ApplicationException,
            org.omg.CORBA.portable.RemarshalException
    {
      ORB orb = null;
      CorbaMessageMediator messageMediator = null;
      try {
        messageMediator = (CorbaMessageMediator)
            outputObject.getMessageMediator();

        orb = (ORB) messageMediator.getBroker();

        if (orb.subcontractDebugFlag) {
            dprint(".marshalingComplete->: " + opAndId(messageMediator));
        }

        CDROutputObject cdrOutputObject = (CDROutputObject) outputObject;

        //
        // Create server-side input object.
        //

        ByteBufferWithInfo bbwi = cdrOutputObject.getByteBufferWithInfo();
        cdrOutputObject.getMessageHeader().setSize(bbwi.byteBuffer, bbwi.getSize());
        final ORB inOrb = orb;
        final ByteBuffer inBuffer = bbwi.byteBuffer;
        final Message inMsg = cdrOutputObject.getMessageHeader();
        CDRInputObject cdrInputObject = AccessController
                .doPrivileged(new PrivilegedAction<CDRInputObject>() {
                    @Override
                    public CDRInputObject run() {
                        return new CDRInputObject(inOrb, null, inBuffer,
                                inMsg);
                    }
                });
        messageMediator.setInputObject(cdrInputObject);
        cdrInputObject.setMessageMediator(messageMediator);

        //
        // Dispatch
        //

        // REVISIT: Impl cast.
        ((CorbaMessageMediatorImpl)messageMediator).handleRequestRequest(
            messageMediator);

        // InputStream must be closed on the InputObject so that its
        // ByteBuffer can be released to the ByteBufferPool. We must do
        // this before we re-assign the cdrInputObject reference below.
        try { cdrInputObject.close(); }
        catch (IOException ex) {
            // No need to do anything since we're done with the input stream
            // and cdrInputObject will be re-assigned a new client-side input
            // object, (i.e. won't result in a corba error).

            if (orb.transportDebugFlag) {
               dprint(".marshalingComplete: ignoring IOException - " + ex.toString());
            }
        }

        //
        // Create client-side input object
        //

        cdrOutputObject = (CDROutputObject) messageMediator.getOutputObject();
        bbwi = cdrOutputObject.getByteBufferWithInfo();
        cdrOutputObject.getMessageHeader().setSize(bbwi.byteBuffer, bbwi.getSize());
        final ORB inOrb2 = orb;
        final ByteBuffer inBuffer2 = bbwi.byteBuffer;
        final Message inMsg2 = cdrOutputObject.getMessageHeader();
        cdrInputObject = AccessController
                .doPrivileged(new PrivilegedAction<CDRInputObject>() {
                    @Override
                    public CDRInputObject run() {
                        return new CDRInputObject(inOrb2, null, inBuffer2,
                                inMsg2);
                    }
                });
        messageMediator.setInputObject(cdrInputObject);
        cdrInputObject.setMessageMediator(messageMediator);

        cdrInputObject.unmarshalHeader();

        InputObject inputObject = cdrInputObject;

        return processResponse(orb, messageMediator, inputObject);

      } finally {
        if (orb.subcontractDebugFlag) {
            dprint(".marshalingComplete<-: " + opAndId(messageMediator));
        }
      }
    }

    protected void dprint(String msg)
    {
        ORBUtility.dprint("SharedCDRClientRequestDispatcherImpl", msg);
    }
}

// End of file.
