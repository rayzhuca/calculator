# Calculator
Calculator is a library for calculating a string into a double in Java.

## How to use
### Calculate a number
```java
public static void main(String[] args) throws Exception {
  System.out.println(new Calculator().calculate("5+2/4"));
}
```
Output:
```
5.5
```

### Block an operator
```java
public static void main(String[] args) {
  Calculator calculator = new Calculator();
  Manager manager = new Manager();
  manager.removeOperation(Operator.ADD);
  try {
    System.out.println(calculator.calculate("2+2"));
  } catch (Exception e) {
    e.printStackTrace();
  }
}
```
Output:
```
java.lang.IllegalArgumentException: Blocked operator
```

### Check validity before calculating
```java
public static void main(String[] args) {
  Calculator calculator = new Calculator();
  Scanner scanner = new Scanner(System.in);
  Manager manager = new Manager();
  
  manager.removeOperation(Operator.ADD);   
  System.out.println(calculator.checkValidity("2+2"));
}
```
Output:
```
false
```

## Bugs
There are plenty of bugs and problems with this library, for example operations that is unary may not currently work.
Please post bugs into Issues page for me to fix.

## Upcoming updates
- Unary operators
- Postfix operators, like factorials
- Algebra
- Ability to save a variable into a calculator instance

## Usage
Anyone is welcome to fork and work on this project, I may accept pull requests only if they are reasonable.
