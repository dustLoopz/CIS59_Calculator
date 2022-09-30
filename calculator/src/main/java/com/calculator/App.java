package com.calculator;
import java.util.*;

/*
 * Chavvi Calculator Application
 *Command Line Calculator with User Interface for 2 Variables
 * Dustin Lopez
 * CIS-059 SJCC
 * Menu Functions and User Input Functions based on code 
 * provided by Professor Gabriel Solomon.
 */

public class App 
{
	public static void main( String[] args ){
		//Storing user input, results, and command.
		float result=0, numA=0, numB=0;                                      
		Character command = ' ';                                            
		
		//Creating Scanner object to get user input. 
		Scanner userInput = new Scanner(System.in);                          

		//Menu, Command, and Output Loop
		//Loop Closes Once User Inputs 'q' or 'Q'
		while (command != 'q'){
			printMenu(numA, numB, result, command);
			System.out.println("Enter command: ");

			//We use a method getCommand to get command from the user 
			//and store it in variable "command", passing Scan object
			//"userInput" to allow the method to get user input
			command = getCommand(userInput);
			
			//Since Java cannot pass values by reference and 
			//all primitive and equivalent objects are immutable,
			//we cannot capture persistent values for A and B
			//without creating a mutable object. Since we were 
			//told not to use classes, We have to be ineficient 
			//and parse commands 'a', 'b', and 'c' to modify
			//persistent variables. 
			if (command == 'a'){                                   //Storing value for A
				numA = getNumber(userInput);
			} else if (command == 'b'){                            //Storing value for B
				numB = getNumber(userInput);
			} else if (command == 'c'){                            //Clearing Values, Results
				numA=0;
				numB=0;
				result=0;
			} else{                                                          
				result = executeCommand(command, numA, numB);     //Executing Operation
			}                   
			
		}

		userInput.close();                                          //Closing Scanner Object   
	}

	// Calculator functions
	// Input: Valid char variable for calculation type, error, or quitting. 
	// Output: Console print out with results and returning a float value from operation
	private static Float executeCommand(char command, float num1, float num2) {
     	float output=0;
  	switch (command) {
     	case 'x':                                                          //Multiplication
        case '*':                                                            //Multiplication
        	output = num1 * num2; 
            System.out.println("Result = " + printNum(output));
            break;
        case '/':                                                            //Division
            if (num2 != 0){
                output = num1 / num2; 
                System.out.println("Result = " + printNum(output));
            } else{ 
                System.out.println("Error! Cannot divide by 0!"); 
            }
            break;
        case '+':                                                            //Addition
            output = num1 + num2; 
            System.out.println("Result = " + printNum(output));
            break;
        case '-':                                                            //Subtraction
            output = num1 - num2; 
            System.out.println("Result = " + printNum(output));
            break;
        case 'q':                                                            //Quitting
			System.out.println("Thank you for using our calculator!");
			break;
        case 'e':                                                            //Error
			System.out.println("ERROR: Invalid commmand");
			break;
        default:                                                             //Unknown
          System.out.println("ERROR: Unknown commmand");
      }
  
      return output;                                                         //Returning result
    }

	// Prints the menu
	// Input: Persistent values that are displayed
	// Ouput: Formatted menu printed to console
	public static void printMenu(float num1, float num2, float result, char lastOperation) {

		String strA = printNum(num1);
		String strB = printNum(num2);
		String strResult = printNum(result);
		
		//Header
		printMenuLine();
		System.out.println("ChavviCalc");
		printMenuLine();
		
		//Displaying current values and the last operation
		System.out.println("A= " + strA + "\t" + "B= " + strB);
		if(checkOperation(lastOperation, num2)){
			System.out.println("Last Operation: " + strA + " " 
			+ lastOperation + " " + strB + " = " + strResult);
		}
		
		//Command Options
		printMenuLine();
		printMenuCommand('a', "Enter Value for A");
		printMenuCommand('b', "Enter Value for B");
		printMenuCommand('-', "+");
		printMenuCommand('*', "Multiply");
		printMenuCommand('/', "Divide");
		printMenuCommand('c', "Clear");
		printMenuCommand('q', "Quit");
		printMenuLine();
	}

	// Get first character from input
	// Adapted from input capture function written by Professor Gabriel Solomon
	private static Character getCommand(Scanner userInput) {
		Character command = ' ';
		 
		String rawInput = userInput.nextLine();

		if (rawInput.length() == 1) {
			rawInput = rawInput.toLowerCase();
			command = rawInput.charAt(0);
		} else{ command = 'e'; }

		return command;
	}


	// Get value from user
	private static float getNumber(Scanner userInput) {
		Boolean success = true;
		float output=0;

		do{
			try {
				System.out.println("Please enter an integer or floating point number.");
				output = userInput.nextFloat();
				success = true;
			//Invalid input exception caught to prevent program crashing
			} catch(Exception e) {                                   			
				System.out.println("Invalid Input!");
				success = false;                                     //Setting false flag
				userInput.next();                                    //Clearing Buffer
			}   
		} while(success == false);
			userInput.nextLine(); 
		return output;
    }

	// Menu functions
	private static Boolean checkOperation(Character command, float numB) {
		if (command == '+' || command == '*' || command == '-' || command == 'x' ){return true;}
        else if(command == '/' && numB != 0 ){return true;}
		else{ return false; }
	}
	
	//Function formats command 
	//Written by Professor Gabriel Solomon
	private static void printMenuCommand(Character command, String desc) {
		System.out.printf("%s\t%s\n", command, desc);
	}
	
	private static String printNum(float number) {
		//Formatting Values for 3 decimal places for any number of significant figures
		//Picks beftween scientific notation and decimal floating point.
		//Stores precision. Default Precision for single digit number using scientic notation.
		float absNum = Math.abs(number);
		char format = 'G';                                                        
		int precision = 4;                                                         
		
		
		//For numbers less than 1, maintains 3 decimal places.
		if (absNum >= 0 && absNum <1){ precision = 3; format = 'f';}               
		else if(absNum>=10000 || absNum<10){ precision = 4; }
		//Maintains 3 decimal points for double digit numbers                     
		else if (absNum >= 10 && absNum <100){ precision = 5; }
		//Maintains 3 decimal points for triple digit numbers
		else if (absNum >= 100 && absNum <1000){ precision = 6; }
		//Maintains 3 decimal points for four digit numbers                 
		else if (absNum >= 1000 && absNum <10000){ precision = 7;}                

		String fNum = String.format("%."+precision+format, number);    
		return fNum;
	}
	
	//Function Prints a line on the screen
	//Written by Professor Gabriel Solomon
	private static void printMenuLine() {
		System.out.println(
			"----------------------------------------------------------"
		);
	}
}