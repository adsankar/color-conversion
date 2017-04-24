import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Source {
	private static int caseNumber = 1;
	private static Random r = new Random(465);
	private static int[] bkgRGB = new int[3];
	private static int[] textRGB = new int[3];
	private static JFrame jf;
	private static JSlider opacitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
	private static ChangeListener cl;
	private static ActionListener al;
	private static int opacity = 100;
	private static JLabel hexColor = new JLabel(" ");
	private static JLabel RGBColor = new JLabel(" ");
	private static JLabel HSVColor = new JLabel(" ");
	private static JPanel panel = new	JPanel();
	private static JLabel filler = new JLabel("Lorem Ipsum");
	private static JButton make = new JButton("Create");
	
	private static JRadioButton changeBoth = new JRadioButton("Change Background and Text", true);
	private static JRadioButton holdBkg = new JRadioButton("Keep Background Color", false);
	private static JRadioButton holdText = new JRadioButton("Keep Text Color", false);
	
	//TODO docs?
	
	public static void main(String[] args) {
		new Source();
		
		try{	
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());	
		}
		catch(Exception ex){
			System.out.println("Couldn't set Look & Feel");
		}
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(new JLabel("Color Generator"));
		
		ButtonGroup group = new ButtonGroup();
		group.add(changeBoth);
		group.add(holdBkg);
		group.add(holdText);
		
		opacitySlider.addChangeListener(cl);
		
		panel.add(changeBoth);
		panel.add(holdBkg);
		panel.add(holdText);
		
		panel.add(RGBColor);
		panel.add(hexColor);
		panel.add(HSVColor);
		
		make.setText("Generate");
		panel.add(make);
		panel.add(new JLabel("Opacity"));
		panel.add(opacitySlider);
		
		changeBoth.addActionListener(al);
		holdBkg.addActionListener(al);
		holdText.addActionListener(al);
		
		make.addActionListener(al);
		
		panel.add(filler);
		jf.add(panel);
		
		jf.pack();
		jf.setVisible(true);
	}
	
	private static void generate(int caseNumber){
		switch (caseNumber){
		case 1://change text and background
			bkgRGB = newColor();
			textRGB = newColor();
			update();
			break;
		case 2://change only text color
			textRGB = newColor();
			update();
			break;
		case 3://change only background color
			bkgRGB = newColor();
			update();
			break;
		}
	}
	
	private static void update(){
		filler.setForeground(new Color(textRGB[0], textRGB[1], textRGB[2], opacity));
		panel.setBackground(new Color(bkgRGB[0], bkgRGB[1], bkgRGB[2]));
		
		hexColor.setText("Hex: "+toHex(bkgRGB[0], bkgRGB[1], bkgRGB[2])+"    "+toHex(textRGB[0], textRGB[1], textRGB[2]));
		HSVColor.setText("HSV: "+toHSV(bkgRGB[0], bkgRGB[1], bkgRGB[2])+"    "+toHSV(textRGB[0], textRGB[1], textRGB[2]));
		RGBColor.setText("RGB: "+toRGB(bkgRGB[0], bkgRGB[1], bkgRGB[2])+"    "+toRGB(textRGB[0], textRGB[1], textRGB[2]));
	}
	
	private Source(){
		super();
		filler.setFont(new Font("Arial", Font.BOLD, 20));
		jf = new JFrame("Color Generator Version 1");
		
		cl = new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					opacity =  (int)source.getValue();
					update();
				}
				
			}
			
		};
		
		al = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == make){
					//System.out.println("test");
					generate(caseNumber);
					update();
				}
				
				if (e.getSource()== changeBoth){
					System.out.println("case 1");
					caseNumber = 1;
				}
				if (e.getSource()== holdBkg){
					System.out.println("case 2");
					caseNumber = 2;
				}
				if (e.getSource()== holdText){
					System.out.println("case 3");
					caseNumber = 3;
				}
				
			}
		};
			

	}
	
	private static String toHSV(int r, int g, int b){
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		float[] HSV = new float[3];
		Color.RGBtoHSB(r, g, b, HSV);
		
		return "("+df.format(HSV[0])+", "+df.format(HSV[1])+", "+df.format(HSV[2])+")";
	}
	
	private static String toHex(int r, int g, int b){
		return String.format("#%02X%02X%02X", r, g, b);
	}
	
	private static String toRGB(int r, int g, int b){
		return "("+ r+", "+ g+ ", "+ b+")";
	}
	
	private static int[] newColor(){
		int[] RGB = new int[3];
		RGB[0] = r.nextInt(256);
		RGB[1] = r.nextInt(256);
		RGB[2] = r.nextInt(256);
		return RGB;
	}

}
