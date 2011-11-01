/*
 * (c) Copyright 2000, 2001, 2002, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * [See end of file]
 * $Id: Filter.java,v 1.2 2010-11-26 19:58:47 andy_seaborne Exp $
 */

package com.hp.hpl.jena.util.iterator;

import java.util.Iterator;

/** 
    boolean functions wrapped to be used in filtering iterators.
    
    @author jjc, kers
*/
public abstract class Filter<T>
    {
    /**
        Answer true iff the object <code>o</code> is acceptable. This method
        may also throw an exception if the argument is of a wrong type; it
        is not required to return <code>false</code> in such a case.
    */
	public abstract boolean accept( T o );
    
    public ExtendedIterator<T> filterKeep( Iterator<T> it )
        { return new FilterKeepIterator<T>( this, it ); }
    
    public Filter<T> and( final Filter<T> other )
        { return other.isAny() ? this : new Filter<T>()
            { @Override public boolean accept( T x ) 
                { return Filter.this.accept( x ) && other.accept( x ); } 
            };
        }
    
    /**
        Answer true iff this filter will deliver true for any argument. Should never
        be overridden except by classes generated by any() below.
    */
    public boolean isAny()
        { return false; }
    
    /** 
        A Filter that accepts everything it's offered.
        @deprecated use Filter.any()
    */
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Knowingly suppressed - maximum backward compatibility. 
    @Deprecated public static final Filter any = new Filter()
        { 
        @Override public final boolean isAny() { return true; }
        
        @Override public final boolean accept( Object o ) { return true; } 
        
        @Override public Filter and( Filter other ) { return other; }
        
        @Override public ExtendedIterator filterKeep( Iterator it )
            { return WrappedIterator.create( it ); }
        };
        
    public static <T> Filter<T> any() 
        {  
        return new Filter<T>()
            {
            @Override public final boolean isAny() { return true; }
            
            @Override public final boolean accept( T o ) { return true; } 
            
            @Override public Filter<T> and( Filter<T> other ) { return other; }
            
            @Override public ExtendedIterator<T> filterKeep( Iterator<T> it )
                { return WrappedIterator.create( it ); }
            };
        }
    }

/*
 *  (c) Copyright 2000, 2001, 2002, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $Id: Filter.java,v 1.2 2010-11-26 19:58:47 andy_seaborne Exp $
 *
 */