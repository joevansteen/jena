/*
 * (c) Copyright 2001-2003, Hewlett-Packard Company, all rights reserved.
 * [See end of file]
 */

/* Generated By:JJTree: Do not edit this line. Q_TextLiteral.java */

package com.hp.hpl.jena.rdql.parser;

import com.hp.hpl.jena.rdf.model.* ;

class Q_TextLiteral extends ParsedLiteral {

    Q_URI datatype = null ;
    String langTag = null ;
    String seen = null ;

  Q_TextLiteral(int id) {
    super(id);
  }

  Q_TextLiteral(RDQLParser p, int id) {
    super(p, id);
  }

  void set(String s)
  {
    seen = s ; 
    // Remove string quotes
    s = s.substring(1,s.length()-1) ;
    super.setString(unescape(s,'\\')) ;
  }
  
    public void jjtClose()
    {
        int n = jjtGetNumChildren();
        for (int i = 0; i < n; i++)
        {
            Node node = this.jjtGetChild(i);

            if (node instanceof Q_Identifier)
                langTag = ((Q_Identifier) node).id;

            if (node instanceof Q_URI)
                datatype = (Q_URI) node ;
        }
        if ( langTag != null )
            seen = seen+"@"+langTag ;
        if ( datatype != null )
            seen = seen+"^^"+datatype.asQuotedString() ;
    }
  
    public void fixup(Q_Query qnode)
    {
        // Must wait until tany QName is resolved.
        String tmp = null ;
        if ( datatype != null )
        {
            if ( ! datatype.isSet )
                datatype.fixup(qnode) ;
            tmp = datatype.valueString() ;
        }
                 
        Literal l = model.createTypedLiteral(super.getString(), langTag, tmp) ;
        super.setRDFLiteral(l) ; 
    }
  
    public String asQuotedString()
    {
        return seen ;
        //return super.asQuotedString() ;
    }
  
  // Utility to remove escapes
  static String unescape(String s, char escape)
  {
      for ( int i = 0 ; i < s.length() ; i++ )
      {
          if ( s.charAt(i) != escape )
              continue ;
          // Escape
          if ( i >= s.length()-1 )
              // At end - skip.
              continue ;
          char ch2 = s.charAt(i+1) ;
          if ( ch2 == 'n' ) ch2 = '\n' ;
          if ( ch2 == 't' ) ch2 = '\t' ;
          if ( ch2 == 'r' ) ch2 = '\r' ;
          if ( ch2 == 'b' ) ch2 = '\b' ;
          // Other escapes are just the literal character (e.g. ' ")
          s = s.substring(0,i)+ch2+s.substring(i+2) ;
          // s got shorter so i now points to char after escape and i+1
          // is after ch2.  No fix up needed.
      }
      return s ;
  }
}

/*
 *  (c) Copyright Hewlett-Packard Company 2001-2003
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
 *
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
 * This product includes software developed by the
 * Apache Software Foundation (http://www.apache.org/).
 *
 */
