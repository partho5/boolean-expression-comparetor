
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author partho
 */
public class Library {
    
    public void generateBoolString(String arrString[][], int n){ //n=num of lieral
        int rows=(int) Math.pow(2, n);
        //System.out.println("n="+n);
        for (int i = 0; i < rows; i++) {
            for (int j = n-1; j >=0; j--) {
                try {
                    arrString[i][j]=( (i/(int) Math.pow(2, j))%2 ==1 )? "true":"false";
                } catch (Exception e) {
                    System.out.println(n);
                }
            }
        }
    }
    
    public int numberOfLiteral(String expression1,String expression2){
        return literals(expression1, expression2).length;
    }
    
    public int numberOfLiteral(String expression){
        return literals(expression).length;
    }
    
    public String[] literals(String expression1,String expression2){
        String arrayToReturn[];
        ArrayList list=new ArrayList();
        int l1=expression1.length(), l2=expression2.length();
        char c;
        
        for (int i = 0; i < l1; i++) {
            c=expression1.charAt(i);
            if( !list.contains(c) && Character.isAlphabetic(c) ){
                list.add(c);
            }
        }
        
        for (int i = 0; i < l2; i++) {
            c=expression2.charAt(i);
            if( !list.contains(c) && Character.isAlphabetic(c) ){
                list.add(c);
            }
        }
        
        int l=list.size();
        arrayToReturn=new String[l];
        for (int i = 0; i < l; i++) {
            arrayToReturn[i]=String.valueOf(list.get(i));
        }
        
        return arrayToReturn;
    }
    
    public String[] literals(String expression1){
        String arrayToReturn[];
        ArrayList list=new ArrayList();
        int l1=expression1.length();
        char c;
        
        for (int i = 0; i < l1; i++) {
            c=expression1.charAt(i);
            if( !list.contains(c) && Character.isAlphabetic(c) ){
                list.add(c);
            }
        }
        
        int l=list.size();
        arrayToReturn=new String[l];
        for (int i = 0; i < l; i++) {
            arrayToReturn[i]=String.valueOf(list.get(i));
        }
        
        return arrayToReturn;
    }
    
    public String addOperators(String ex1){
        String s1,s2;
        for (int i = 0; i <ex1.length() ; i++) {
            if( i>0 && (ex1.charAt(i)=='!' || Character.isAlphabetic(ex1.charAt(i)) ) && Character.isAlphabetic(ex1.charAt(i-1)) ){
                s1=ex1.substring(0, i);
                s2=ex1.substring(i, ex1.length());
                ex1=s1+" && "+s2;
            }
        }
        ex1=ex1.replace("+"," || ");
        return ex1;
    }
    
    public boolean isEqual(String expression1,String expression2){
        
        expression1=addOperators(expression1);
        expression2=addOperators(expression2);
        
        String expression1_backup=expression1;
        String expression2_backup=expression2;
        
        int n=numberOfLiteral(expression1, expression2);
        int rows=(int)Math.pow(2, n);
        
        ArrayList result_for_expression1=new ArrayList();
        ArrayList result_for_expression2=new ArrayList();
        boolean result;
        
        ScriptEngine engine=new ScriptEngineManager().getEngineByName("JavaScript");
        
        String all_booString[][]=new String[rows][n];
        generateBoolString(all_booString, n);
        String nThLiterall;
        String ALL_LITERALS[]=literals(expression1, expression2).clone();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < n; j++) {
                nThLiterall=ALL_LITERALS[j];
                System.out.print(nThLiterall+"="+all_booString[i][j]+"     ");
                expression1=expression1.replace(nThLiterall,all_booString[i][j]);
            }
            
            System.out.println();
            System.out.println(expression1_backup);
            try {
                result=(String.valueOf(engine.eval(expression1)))=="true"? true:false ;
                result_for_expression1.add(result);
                System.out.println(expression1+" = "+result);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("----------------");
            expression1=expression1_backup;
        }
        System.out.println("===========================================");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < n; j++) {
                nThLiterall=ALL_LITERALS[j];
                System.out.print(nThLiterall+"="+all_booString[i][j]+"     ");
                expression2=expression2.replace(nThLiterall,all_booString[i][j]);
            }
            System.out.println();
            System.out.println(expression2_backup);
            try {
                result=(String.valueOf(engine.eval(expression2)))=="true"? true:false ;
                result_for_expression2.add(result);
                System.out.println(expression2+" = "+result);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("---------------");
            expression2=expression2_backup;
        }
        
        System.out.println(result_for_expression1);
        System.out.println(result_for_expression2);
        
        for (int i = 0; i < rows; i++) {
            if(result_for_expression1.get(i)!=result_for_expression2.get(i)){
                return false;
            }
        }
        
        return true;
    }
    
    public void generateTruthTable(JTable table,String expression){
        table.setModel(new DefaultTableModel());
        DefaultTableModel tableModel=(DefaultTableModel) table.getModel();
        
        String ALL_LITERALS[]=literals(expression);
        int num_of_literals=ALL_LITERALS.length;
        
        for (int i = 0; i < num_of_literals; i++) {
            tableModel.addColumn(ALL_LITERALS[i]);
        }
        tableModel.addColumn("Output");
        
        int rows=(int) Math.pow(2,num_of_literals);
        String all_bool_string[][]=new String[rows][num_of_literals];
        generateBoolString(all_bool_string, num_of_literals);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <num_of_literals; j++) {
                //System.out.print(all_bool_string[i][j]+"    ");
            }
            //System.out.println();
        }
        
        
        for (int i = 0; i < rows; i++) {
            tableModel.addRow(new Object[]{}); 
        }
        
        ScriptEngine engine=new ScriptEngineManager().getEngineByName("JavaScript");
        String nThLieral;
        
        expression=addOperators(expression);
        String expression_backup=expression;
        
        ArrayList result_for_expression=new ArrayList();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < num_of_literals; j++) {
                nThLieral=ALL_LITERALS[j];
                expression=expression.replace(nThLieral,all_bool_string[i][j]);
            }
            try {
                int result=String.valueOf(engine.eval(expression))=="true"? 1:0;
                result_for_expression.add(result);
            } catch (Exception e) {
            }
            expression=expression_backup;
        }
        
        System.out.println(result_for_expression);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < num_of_literals; j++) {
                int cellVal=all_bool_string[i][j]=="true"? 1:0;
                table.setValueAt(cellVal,i,j);
            }
            table.setValueAt(result_for_expression.get(i),i,num_of_literals);
        }
        //table.removeColumn(table.getColumnModel().getColumn(1));
    }
}
