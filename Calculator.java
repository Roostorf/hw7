package edu.ics211.h07;

import java.util.Stack;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
    // create a stack
    Stack<Number> stack = new Stack<Number>();

    String[] token =  expression.split(" "); // split string into tolkens by split by space
    
    System.out.println("token array length :" + token.length);
    
    /*
    if ((!ArrayUtils.contains(token, "+") // if the array doesnt contain an operator and is larger than one element
        || !ArrayUtils.contains(token, "-") 
        || !ArrayUtils.contains(token, "*") 
        || !ArrayUtils.contains(token, "/"))
        && token.length > 1) {
      
      System.out.println("error 0");
      throw new InvalidExpressionException();
    }*/
    
    if (token.length == 1 && NumberUtils.isParsable(token[0])) // If the array is only one, and it is a number
    {
      if (token[0].contains("."))  //If double
      {
        return Double.parseDouble(token[0]); // return numeric double value

      } else {
        
        return Integer.parseInt(token[0]); // else return numeric int
        
      }
 

    } else if (token.length > 1) {
      
      for (int i = 0; i < token.length; i ++)
      {
        System.out.println(token[i]);
        System.out.println(token.length);
        // If token is numeric
        if (StringUtils.isNumeric(token[i])) {
          // if element is a number try adding to stack
        
          try // Will work if integer
          {
            stack.push(Integer.parseInt(token[i]));    
            
          } catch (NumberFormatException nfe)
          {
            throw new NumberFormatException();
          }
          if (token[i].contains("."))  //If double
          {
            stack.push(Double.parseDouble(token[i]));

          } 
        } else if ((token[i] == "+" 
            || token[i] == "-"  
            || token[i] == "/"  
            || token[i] == "*") 
            && stack.size() > 2) {
          // if element is an operator take out last two numbers from stack and perform operation
          System.out.println("contains operator!");
          performOperation(token[i], stack);

        } else { 
          // if not number or operator we have a problem
          System.out.println("error 1");
          throw new InvalidExpressionException();    
        }          
      }
    } else {
      System.out.println("error 2");
      throw new InvalidExpressionException();
    }
    

    
    

    


    
    return stack.pop();
    
    /* 
    if (!stack.empty()) {
      return stack.pop();
    } else {
      throw new InvalidExpressionException();
    }*/
  }




  /**
   * Performs an operation when encountering operator.
   * @param string an operator.
   */
  private void performOperation(String operator, Stack<Number> stack) {
    
    double popA = stack.pop().doubleValue();
    double popB = stack.pop().doubleValue();
    
    
    
    
    if (operator == "+")
    {      
      stack.push(popA + popB);

    } else if (operator == "-")
    {
      stack.push(popA - popB);
    } else if (operator == "/")
    {
      stack.push(popA / popB);
    } else if (operator == "*")
    {
      stack.push(popA * popB);
    }

  }

  @Override
  public Number preFixCalculate(String expression) throws InvalidExpressionException {
    // TODO Auto-generated method stub
    
    throw new UnsupportedOperationException();
  }

}
