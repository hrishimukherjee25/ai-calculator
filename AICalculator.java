package chatgpt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// CONTROLLER
public class AICalculator {
	
	private static CustomExpressionEvaluator model;
	private static CustomCalculator view;
	
	public static void main(String[] args) {
		model = new CustomExpressionEvaluator();
		view = new CustomCalculator();
		
        // Example expressions
        String expression1 = "e + f - imm * 2";
        String expression2 = "ce + f^-1 * ceta";
        String expression3 = "tdidf / pagerank + no";
		
		System.out.println(expression1 + " = " + model.evaluate(expression1));
		System.out.println(expression2 + " = " + model.evaluate(expression2));
		System.out.println(expression3 + " = " + model.evaluate(expression3));
	}

}
