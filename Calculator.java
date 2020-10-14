package edu.ics211.h07;

import java.util.Stack;
import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;



/**
 * Represents a PostFixCalculator.
 *
 * @author Constantine Peros
 */
public class Calculator implements ICalculator {
  private static Calculator instance;
  
  /**
   * returns singleton instance.
   * @return the singleton instance.
   *
   */
  public static Calculator getInstance() {
    if (instance == null) {
      instance = new Calculator();
    }
    return instance;
  }
  
  /**
   * Calculates an expression.
   * @param expression a String of the postfix expression
   * @return result of the equation as a Number
   * @throws InvalidExpressionException if expression isn't valid postfix
   */
  private Calculator() {
    // Hidden since is singleton
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    //throw new UnsupportedOperationException();


  }


  @Override
  public Number postFixCalculate(String expression) throws InvalidExpressionException {
    Stack<Number> stack = new Stack<Number>();  // create a stack
    String[] token =  expression.split(" ");    // split string into tolkens by split by space
    
    System.out.println("token array length :" + token.length);
    
    if (token.length > 1) {
      if (!NumberUtils.isParsable(token[1])) {
        
        System.out.println("error 1 Second digit in string is non numeric");
        throw new InvalidExpressionException();
        
      }
    }
    if (token.length == 1 && NumberUtils.isParsable(token[0])) // If the array is only one, and it is a number
    {
      if (token[0].contains("."))  //If double
      {
        return Double.parseDouble(token[0]); // return numeric double value

      } else {
        
        return Integer.parseInt(token[0]); // else return numeric int
        
      }
 

    } else if (token.length > 1 
        && (ArrayUtils.contains(token, "+")
            || ArrayUtils.contains(token, "-")
            || ArrayUtils.contains(token, "/")
            || ArrayUtils.contains(token, "*"))) { // If the array is longer than one and contains an operator
      
      for (int i = 0; i < token.length; i ++)
      {
        System.out.println("token value is: " + token[i]);
        
        // if token is operator 
        if ((token[i].equals("+") 
            || token[i].equals("-") 
            || token[i].equals("*") 
            || token[i].equals("/"))) {
          // if element is an operator take out last two numbers from stack and perform operation
          System.out.println("contains operator!");
          performOperation(token[i], stack);
          
          

        }
        
        
        
        
        // If token is numeric
        if (NumberUtils.isParsable(token[i])) {
          // if element is a number try adding to stack
          
          if (token[i].contains("."))  //If double
          {
            stack.push(Double.parseDouble(token[i]));
            System.out.println("Double " + token[i] + " added to the number stack");

          } else {
            
            try {
              stack.push(Integer.parseInt(token[i]));
              System.out.println("Integer " + token[i] + " added to the number stack");
              
            } catch (NumberFormatException nfe)
            {
              throw new NumberFormatException();
            }  
          }   
        }
             
      }
    } else {
      System.out.println("error 2 Contains invalid char or no Operator ");
      throw new InvalidExpressionException();
    }

    
    return stack.pop();

  }

  
  

  /**
   * Performs an operation when encountering operator.
   * @param string an operator.
   */
  private void performOperation(String operator, Stack<Number> stack) {
    
    System.out.println("we have operation");
    Number popNum = stack.pop(); // pop the top
    
    System.out.println("popped number equals: " + popNum);
    
    if (popNum instanceof Double) {
      System.out.println("we have Double");
      // Do double math
      double popDblA = popNum.doubleValue();
      double popDblB = stack.pop().doubleValue();
      
      popDblA = dblMath(popDblA, popDblB, operator);
      
      System.out.println("for the result of the operation we got :" + popDblA);
      
      stack.push(popDblA);
      System.out.println(stack.peek() + " was added to the stack");
    }
    
    //Number popA = stack.pop();
    if (popNum instanceof Integer) {
      System.out.println("we got integer");
      
      int popIntA = popNum.intValue(); // if Number popped is integer, assign it to Int popA
      
      popNum = stack.pop(); // pop the top again
      
      
      if (popNum instanceof Integer) { // if next number popped is integer
        // Do int Math
        System.out.println("we got integer for next value too!");
        popIntA = intMath(popIntA, popNum, operator);
        
        System.out.println("for the result of the operation we got :" + popIntA);
        
        stack.push(popIntA);
        System.out.println(stack.peek() + " was added to the stack");
        
        
      } else if (popNum instanceof Double) { // if next number popped is double
        System.out.println("we got double for next value");
        System.out.println("we have Double");
        // Do double math
        double popDblA = popNum.doubleValue();
        double popDblB = stack.pop().doubleValue();
        
        popDblA = dblMath(popDblA, popDblB, operator);
        
        System.out.println("for the result of the operation we got :" + popDblA);
        
        stack.push(popDblA);
        System.out.println(stack.peek() + " was added to the stack");
        
      } else {
        System.out.println("error 4");
        //throw new InvalidExpressionException();
        
      }
      
      
      

    } 
    

    //Double popA = stack.pop().doubleValue(); // if Number popped is double, assign it to Int popA
      
    // gotta do double math
      
      

    //}


  }

  /**
   * Does Double Math.
   * @param popDblA popDblA.
   * @param popDblB popDblB.
   * @param operator operator.
   * @return
   */
  private double dblMath(double popDblA, double popDblB, String operator) {
    
    if (operator.equals("+"))
    {      
      System.out.println("we got Double add!");
      popDblA = popDblA + popDblB;

    } else if (operator.equals("-"))
    {
      System.out.println("we got Integer sub!");
      popDblA = popDblB - popDblA;
      
    } else if (operator.equals("*"))
    {
      System.out.println("we got Integer mult!");
      popDblA = popDblA * popDblB;
      
    } else if (operator.equals("/"))
    {
      System.out.println("we got Integer div!");
      popDblA = popDblB / popDblA;
    }
    
    // TODO Auto-generated method stub
    return popDblA;
  }

  /**
   * Perform Integer Math.
   * @param popIntA Popped Integer
   * @param popNum Next Popped Number
   * @param operator the Operator
   * @return Integer result
   */
  public int intMath(int popIntA, Number popNum, String operator) {
    // Do Integer Math, Only instance of doing integer math should be two integers
    
    int popIntB = popNum.intValue();

    if (operator.equals("+"))
    {      
      System.out.println("we got Integer add!");
      popIntA = popIntA + popIntB;

    } else if (operator.equals("-"))
    {
      System.out.println("we got Integer sub!");
      popIntA = popIntB - popIntA;
      
    } else if (operator.equals("*"))
    {
      System.out.println("we got Integer mult!");
      popIntA = popIntA * popIntB;
      
    } else if (operator.equals("/"))
    {
      System.out.println("we got Integer div!");
      popIntA = popIntB / popIntA;
    }
    return popIntA;
    
  }

  @Override
  public Number preFixCalculate(String expression) throws InvalidExpressionException {
    // TODO Auto-generated method stub
    
    throw new UnsupportedOperationException();
  }

}
