package com.saturn.commons.utils.rpn;



import com.saturn.commons.utils.ArrayUtils;
import com.saturn.commons.utils.MapUtils;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;




/**
 * Calculadora RPN (Reverse Polish Notation)
 * Permite evaluar expresiones aritmeticas y logicas, convirtiendo la expresion
 * de notacion inFija a PostFija y luego evaluando la expresion utilizando una pila
 */
public class RpnCalculator
{
    /** Operadores */
    private static final String OPERATORS="+,-,*,/,<,>,<=,>=,=,!=,AND,OR";

    /** Mapa de operadores */
    private static Map<String,?> operatorMap=ArrayUtils.array2Map(OPERATORS.split(","), 0);

    /** Logger */
    private static Logger log= LogManager.getLogger(RpnCalculator.class);


    private RpnCalculator() {
    }


    public void matching(String x)
    {
        Stack<Character> S = new Stack<Character>();

        for (int i = 0; i < x.length(); i++) {
            char c = x.charAt(i);
            if (c == '(')
                S.push(c);
            else {
                if (c == ')') {
                    if (S.empty())
                        System.out.println("NOT MATCHING !!!");
                    else
                        S.pop();
                }
            }
        }

        if (!S.empty())
            System.out.println("NOT MATCHING !!!");
        else
            System.out.println("MATCHING !!!");
    }



    /**
     * Ejecuta la expresion aritmetica dada.
     *
     * @param expr Expresion Aritmetica a ejecutar
     * @return Resultado de la evaluacion de la expresion
     */
    public static double execute(String expr) throws Exception
    {
        double result=0;
        String postfix = infixToPostfix(expr);
        Stack<Double> stack = new Stack<Double>();
        StringTokenizer scan = new StringTokenizer(postfix, " ");

        try {
            while (scan.hasMoreTokens()) {
                String token = scan.nextToken();

                if (token.matches("[0-9]*[.]?[0-9]+"))
                    stack.push(Double.parseDouble(token));
                else if ("OS.CORES".equalsIgnoreCase(token))
                    stack.push(new Double(Runtime.getRuntime().availableProcessors()));
                else if ("OS.TIME".equalsIgnoreCase(token))
                    stack.push(new Double(System.currentTimeMillis()));
                else {
                    double a, b;

                    if (token.equals("+")) {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b + a);
                    }
                    else if (token.equals("-")) {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b - a);
                    }
                    else if (token.equals("*")) {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b * a);
                    }
                    else if (token.equals("/")) {
                        a = stack.pop();
                        b = stack.pop();
                        stack.push(b / a);
                    }
                }
            }
            result= stack.pop();
        }
        catch (Exception e) {
            log.error("Error ejecutando expresion["+expr+"]", e);
            throw e;
        }

        log.trace("EXPR[{}] -> {}",expr,result);

        return result;
    }



    /**
     * Ejecuta la expresion aritmetica dada.
     *
     * @param expr Expresion Aritmetica a ejecutar
     * @return Resultado de la evaluacion de la expresion
     */
    public static Object execute(String expr,Map<String,String> pars)
    {
        Object result=null;
        String postfix = infixToPostfix(expr);
        Stack<Object> stack = new Stack<Object>();
        StringTokenizer scan = new StringTokenizer(postfix, " ");

        try {
            while (scan.hasMoreTokens()) {
                String token = scan.nextToken();

                // Valor numerico
                if (token.matches("[0-9]*[.]?[0-9]+"))
                    stack.push(Double.parseDouble(token));
                else if ("OS.CORES".equalsIgnoreCase(token))
                    stack.push(new Double(Runtime.getRuntime().availableProcessors()));
                else if ("OS.TIME".equalsIgnoreCase(token))
                    stack.push(new Double(System.currentTimeMillis()));
                // Identificador ?
                else if (token.matches("[a-zA-Z_]+[a-zA-Z0-9_.-]*")) {
                    String number=pars.get(token);
                    log.trace("ID[{}] -> [{}]",token,number);

                    stack.push(number==null ? token : new Double( number ) );
                } else {
                    double a, b;
                    boolean c1,c2;

                    if (token.equals("+")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a + b);
                    } else if (token.equals("-")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a - b);
                    } else if (token.equals("*")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a * b);
                    } else if (token.equals("/")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a / b);
                    } else if (token.equals(">")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a > b);
                    } else if (token.equals(">=")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a >= b);
                    } else if (token.equals("<")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a < b);
                    } else if (token.equals("<=")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a <= b);
                    } else if (token.equals("=")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a == b);
                    } else if (token.equals("!=")) {
                        b = (Double)stack.pop();
                        a = (Double)stack.pop();
                        stack.push(a != b);
                    } else if (token.equals("AND")) {
                        c2 = (Boolean)stack.pop();
                        c1 = (Boolean)stack.pop();
                        stack.push(c1 && c2);
                    } else if (token.equals("OR")) {
                        c2 = (Boolean)stack.pop();
                        c1 = (Boolean)stack.pop();
                        stack.push(c1 || c2);
                    }
                }
            }
            result= stack.pop();
        }
        catch (Exception e) {
            log.error("Error ejecutando expresion["+expr+"] "+MapUtils.map2PrettyString(pars), e);
        }

        log.trace("EXPR[{}] -> {}",expr,result);

        return result;
    }



    /**
     * Convierte la expresion dada a notacion postfija
     * @param in Expresion a convertir
     * @return
     */
    private static String infixToPostfix(String in)
    {
        StringBuilder out = new StringBuilder((int)(in.length()*1.2));
        Stack<String> stack = new Stack<String>();
        StringTokenizer parser= new StringTokenizer(in," ");

        try {
            while(parser.hasMoreTokens()) {
                String token=parser.nextToken();

                // Es operador ?
                if (operatorMap.containsKey(token)) {
                    int tkPriority = priority(token);
                    while (!stack.empty() && priority(stack.peek()) >= tkPriority) {
                        out.append(" ").append(stack.pop());
                    }
                    stack.push(token);
                    out.append(" ");
                }
                else {
                    // Barremos los caracteres
                    boolean isId=false;

                    for (int i = 0; i < token.length(); i++) {
                        char c = token.charAt(i);

                        // Es Operador?
                        if (c == '{')
                            isId=true;
                        else if (c == '}')
                            isId=false;
                        else if (isId)
                            out.append(c);
                        else if (c == '+' || c == ('*') || c == ('-') || c == ('/')) {
                            int tkPriority = priority(c);

                            while (!stack.empty() && priority(stack.peek()) >= tkPriority) {
                                out.append(" ").append(stack.pop());
                            }
                            stack.push(Character.toString(c));
                            out.append(" ");
                        }
                        else if (c == '(')
                            stack.push(Character.toString(c));
                        else if (c == ')') {
                            while (!stack.peek().equals("(")) {
                                out.append(" ").append(stack.pop());
                            }
                            stack.pop();
                        }
                        else
                            out.append(c);
                    }//fin-for
                }

            }//fin-while

            while (!stack.empty()) {
                out.append(" ").append(stack.pop());
            }

        } catch (Exception e) {
            log.error("Error converting["+in+"] to postfix notation! OUT["+out.toString()+"]", e);
        }

        log.trace("INFIX[{}] -> POSTFIX[{}]",in,out);

        return out.toString();
    }



    /**
     * Devuelve la prioridad del operador dado...
     * @param x Operador
     * @return
     */
    public static int priority(Object x) {
        if (x.equals("AND") || x.equals("OR"))
            return 1;
        if (x.equals("<") || x.equals("<=") || x.equals(">") || x.equals(">=") || x.equals("=") || x.equals("!=") )
            return 2;
        else if (x.equals('+') || x.equals('-'))
            return 3;
        else if (x.equals('*') || x.equals('/'))
            return 4;
        else
            return 0;
    }



    /**
     * Probando la Calculadora de Expresiones...
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        double result=RpnCalculator.execute("OS.CORES*2+1");
        System.out.println(result);

        String expr= "(permGenMax/{maxnonheapsize-count.count})*100";
        String posFix = infixToPostfix(expr);
        System.out.println("INFIX["+expr+"] -> POSFIX["+posFix+"]");
    }
}
