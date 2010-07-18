//////////////////////////////////////////////////////////////////////////////
// 
//               Copyright 2010, Starting Block Technologies
//                        www.startingblocktech.com
//
//////////////////////////////////////////////////////////////////////////////

package com.startingblocktech.tcases.generator;

import com.startingblocktech.tcases.VarDef;
import com.startingblocktech.tcases.VarValueDef;
import com.startingblocktech.tcases.conditions.*;
import com.startingblocktech.tcases.util.Asserts;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs tests for {@link TupleCombiner}
 *
 * @version $Revision$, $Date$
 */
public class TestTupleCombiner
  {
  @Test
  public void getTuples_CombinesValid()
    {
    // Given...
    VarValueDef value11 = new VarValueDef( "value-1-1");
    VarValueDef value12 = new VarValueDef( "value-1-2");
    VarValueDef value13 = new VarValueDef( "value-1-3", VarValueDef.Type.FAILURE);
    VarDef      var1    = new VarDef( "var-1").addValue( value11).addValue( value12).addValue( value13);

    VarValueDef value21 = new VarValueDef( "value-2-1", VarValueDef.Type.FAILURE);
    VarValueDef value22 = new VarValueDef( "value-2-2");
    VarDef var2 = new VarDef( "var-2").addValue( value21).addValue( value22);

    VarValueDef value31 = new VarValueDef( "value-3-1");
    VarValueDef value32 = new VarValueDef( "value-3-2");
    VarDef var3 = new VarDef( "var-3").addValue( value31).addValue( value32);
    
    List<VarDef> varDefs = new ArrayList<VarDef>();
    varDefs.add( var1);
    varDefs.add( var2);
    varDefs.add( var3);
    
    // When...
    List<Tuple> tuples = TupleCombiner.getTuples( varDefs, 3);
    
    // Then...
    List<Tuple> expectedTuples = new ArrayList<Tuple>();
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var2, value22),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var2, value22),
          new VarBindingDef( var3, value32)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var2, value22),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var2, value22),
          new VarBindingDef( var3, value32)));

    Asserts.assertSeqEquals( "Tuples", expectedTuples, tuples);
    }
  
  @Test
  public void getTuples_AllPairs()
    {
    // Given...
    VarValueDef value11 = new VarValueDef( "value-1-1");
    VarValueDef value12 = new VarValueDef( "value-1-2");
    VarDef      var1    = new VarDef( "var-1").addValue( value11).addValue( value12);

    VarValueDef value21 = new VarValueDef( "value-2-1");
    VarValueDef value22 = new VarValueDef( "value-2-2");
    VarDef      var2    = new VarDef( "var-2").addValue( value21).addValue( value22);

    VarValueDef value31 = new VarValueDef( "value-3-1");
    VarValueDef value32 = new VarValueDef( "value-3-2");
    VarDef      var3    = new VarDef( "var-3").addValue( value31).addValue( value32);
    
    List<VarDef> varDefs = new ArrayList<VarDef>();
    varDefs.add( var1);
    varDefs.add( var2);
    varDefs.add( var3);
    
    // When...
    List<Tuple> tuples = TupleCombiner.getTuples( varDefs, 2);

    // Then...
    List<Tuple> expectedTuples = new ArrayList<Tuple>();
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var2, value21)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var2, value22)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var3, value32)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var2, value21)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var2, value22)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var3, value32)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var2, value21),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var2, value21),
          new VarBindingDef( var3, value32)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var2, value22),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var2, value22),
          new VarBindingDef( var3, value32)));

    Asserts.assertSeqEquals( "Tuples", expectedTuples, tuples);
    }
  
  @Test
  public void getTuples_AllPairsCompatible()
    {
    // Given...
    VarValueDef value11 = new VarValueDef( "value-1-1").addProperties( "R1", "V1");
    VarValueDef value12 = new VarValueDef( "value-1-2").addProperties( "R1", "V2");
    VarDef      var1    = new VarDef( "var-1").addValue( value11).addValue( value12);

    VarValueDef value21 = new VarValueDef( "value-2-1").addProperties( "R2", "V1");
    VarValueDef value22 = new VarValueDef( "value-2-2").addProperties( "R2", "V2");
    VarDef      var2    = new VarDef( "var-2").addValue( value21).addValue( value22);

    VarValueDef value31 = new VarValueDef( "value-3-1").addProperties( "R3", "V1");
    VarValueDef value32 = new VarValueDef( "value-3-2").addProperties( "R3", "V2");
    VarDef      var3    = new VarDef( "var-3").addValue( value31).addValue( value32);

    value11.setCondition( new Not( new ContainsAny( "R2")));
    value22.setCondition( new AllOf( new ContainsAny( "U1"), new Not( new ContainsAll( "R3", "V1"))));
    value31.setCondition( new AnyOf( new Not( new ContainsAny( "R1")), new Not( new ContainsAll( "R2", "R3"))));
    
    List<VarDef> varDefs = new ArrayList<VarDef>();
    varDefs.add( var1);
    varDefs.add( var2);
    varDefs.add( var3);
    
    // When...
    List<Tuple> tuples = TupleCombiner.getTuples( varDefs, 2);

    // Then...
    List<Tuple> expectedTuples = new ArrayList<Tuple>();
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value11),
          new VarBindingDef( var3, value32)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var2, value21)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var2, value22)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var1, value12),
          new VarBindingDef( var3, value32)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var2, value21),
          new VarBindingDef( var3, value31)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var2, value21),
          new VarBindingDef( var3, value32)));
    expectedTuples.add
      ( new Tuple
        ( new VarBindingDef( var2, value22),
          new VarBindingDef( var3, value32)));

    Asserts.assertSeqEquals( "Tuples", expectedTuples, tuples);
    }

  @Test
  public void getTuples_MinTupleSize()
    {
    // Given...
    List<VarDef> varDefs = new ArrayList<VarDef>();
    varDefs.add( new VarDef( "var-1").addValue( new VarValueDef( "value-1-1")));
    varDefs.add( new VarDef( "var-2").addValue( new VarValueDef( "value-2-1")));
    
    // When...
    try
      {
      List<Tuple> tuples = TupleCombiner.getTuples( varDefs, 0);

      fail( "Unexpected TupleCombiner=" + tuples);
      }

    // Then...
    catch( IllegalArgumentException iae)
      {
      }
    catch( Exception e)
      {
      throw new RuntimeException( "Unexpected exception", e);
      }
    }

  @Test
  public void getTuples_MinVars()
    {
    // Given...
    List<VarDef> varDefs = new ArrayList<VarDef>();
    varDefs.add( new VarDef( "var-1").addValue( new VarValueDef( "value-1-1")));
    varDefs.add( new VarDef( "var-2").addValue( new VarValueDef( "value-2-1")));
    
    // When...
    try
      {
      List<Tuple> tuples = TupleCombiner.getTuples( varDefs, 3);
      fail( "Unexpected TupleCombiner=" + tuples);
      }

    // Then...
    catch( IllegalArgumentException iae)
      {
      }
    catch( Exception e)
      {
      throw new RuntimeException( "Unexpected exception", e);
      }
    }

  @Test
  public void getTuples_ValidValues()
    {
    // Given...
    List<VarDef> varDefs = new ArrayList<VarDef>();
    varDefs.add( new VarDef( "var-1").addValue( new VarValueDef( "value-1-1")));
    varDefs.add( new VarDef( "var-2").addValue( new VarValueDef( "value-2-1", VarValueDef.Type.FAILURE)));
    
    // When...
    try
      {
      List<Tuple> tuples = TupleCombiner.getTuples( varDefs, 2);
      Asserts.assertSeqEquals( "Tuples", (List<Tuple>)null, tuples);
      }

    // Then...
    catch( IllegalStateException ise)
      {
      }
    catch( Exception e)
      {
      throw new RuntimeException( "Unexpected exception", e);
      }
    }
  }



