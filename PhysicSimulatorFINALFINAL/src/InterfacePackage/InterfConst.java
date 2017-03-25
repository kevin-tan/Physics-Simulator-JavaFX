/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePackage;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public interface InterfConst {

    final String MAIN_TITLE = "Physics Simulator";
    final String COURSE_STRING = "Courses";
    final String VIEW_STRING = "View";
    final String ANIMATION_1 = "Animation 1";
    final String ANIMATION_2 = "Animation 2";
    final String IN_CONSTRUCTION = "In Construction";
    final String IN_CONSTRUCTION_MESSAGE = "\t\tThis page is temporarily under construction\nPlease try a different animation using the courses menu";
    final String EXIT_STRING = "Exit";
    final String SPACE_STRING = " ";
    final String FULLSCREEN_STRING = "Toggle Fullscreen";
    final String WELCOME_MESSAGE = "\tWelcome to the Physics Simulator\n Click courses to begin your experience";
    final String RETURN_STRING = "Return";
    final String START_STRING = "Start";
    final String STOP_STRING = "Stop";
    final String RESET_STRING = "Reset";
    final String PAUSE_STRING = "Pause";
    final String HELP_STRING = "Help";
    final float GRAV_ACC = 9.8f;
    final String NYA_TITLE = "Mechanic 203-NYA";
    final String NYB_TITLE = "Electricity and Magnetism 203-NYB";
    final String NYC_TITLE = "Waves, Optics & Modern Physics 203-NYC";
    final String NYA_1 = "Inclined Planes";
    final String NYA_1_FORMULA = "F = ma";
    final String NYA_2 = "Projectile Motion";
    final String NYA_2_FORMULA = "Y = Vyo(t) – ½(g)t^2\nX = Vxo(t) – ½(g)t^2";
    final String NYB_1 = "Ohm's law";
    final String NYB_1_FORMULA = "V = RI";
    final String NYB_2 = "Coulomb's law";
    final String NYB_2_FORMULA = "F = (k |Q1Q2|) / r^2";
    final String NYC_1 = "Pendulum system";
    final String NYC_1_FORMULA = "? = ?max cos(?(t) + f)\n? = sqrt(g/L)\nEtotal = m(g)L(1-cos(?max))";
    final String NYC_2 = "Nuclear Decay";
    final String NYC_2_FORMULA = "N(t) = N(0)*e^(-?*t)\nT_(1/2) = (ln 2) / ?";
    final String TABLE_TAB_STRING = "Table Tab";
    final String GRAPH_TAB_STRING = "Graph Tab";

    //walter
    final String DONE_STRING = "Done";
    final String NYB_1_HELP_MESSAGE = "An object placed on a tilted surface will often slide down the surface."
            + " The rate at which the object slides down the surface is dependent upon how tilted the surface is"
            + " and how big is the gravitational force; the greater the tilt of the surface and/or the mass of"
            + " the object, the faster the rate at which the object will slide down it."
            + " In physics, a tilted surface is called an inclined plane.";
    final String NYC_1_HELP_MESSAGE = "A simple pendulum consists of a relatively massive object "
            + "hung by a string from a fixed support. It typically hangs vertically"
            + " in its equilibrium position. ";
    final String INFORMATION_STRING_SHORT = "Information";
    final String LINE_BREAK = "\n";
    final String YES_STRING = "Yes";
    final String NO_STRING = "No";
    final String CANCEL_STRING = "Cancel";
    final String OK_STRING = "Ok";
    final String LENGTH_STRING = "Length L (in m)";
    final String ANGULAR_VELOCITY_STRING = "Angular Velocity (in rad/sec)";
    final String RESET_TITLE = "Reset Values?";
    final String ANGLE_STRING = "Angle\t";
    final String MASS_STRING = "Mass\t";
    final String QUESTION_STRING = "Would you like to change the values of the variables?\n\n(Note: If you don't want to reset the animation, press Cancel)";
    final String INSTRUCTION_STRING = "Please adjust the following fields with the desired values and press Ok.\n\n";

    // NYB_2
    final String HELP_MESSAGE_NYB_2 = "Two charges initially at opposite charges attract eachother, as shown from the force arrows in black."
                                   + " As the distance between particles increase, the force will decrease."
                                   + " As the charges of the particles increase so will the forces."
                                   + " To begin, press start, and to change any the charges of the particles press the reset button.";
    final int DISTANCE = 5;
    final String DISTANCE_LABEL = "Distance in meters";
    final String FORCE_LABEL = "Force in Netwons";
    final String POS_CHARGE = "+";
    final String NEG_CHARGE = "-";
    final String PARTICLE_ONE = "The charge of the first particle is: ";
    final String PARTICLE_TWO = "The charge of the second particle is: ";
    final String UNITS_CHARGE = "µC";
    final int DEFAULT_CHARGE_ONE = 10;
    final int DEFAULT_CHARGE_TWO = 10;
    final String DEFAULT_CHARGE_SIGN_ONE = POS_CHARGE;
    final String DEFAULT_CHARGE_SIGN_TWO = NEG_CHARGE;
    final Color NEG_CHARGE_COLOR = Color.BLUE;
    final Color POS_CHARGE_COLOR = Color.RED;
    final double K_CONSTANT = 8.99;
    final Duration TIME = Duration.seconds(2);
    final int PARTICLE_ONE_X = 100;
    final int PARTICLE_Y = 300;
    final int PARTICLE_TWO_X = 900;
    
    

    // NYC_2
    final int SQUARE_SIZE = 250;
    final int SQUARE_Y = 125;
    final int SQUARE_ONE_POS_X = 25;
    final int SQUARE_TWO_POS_X = 375;
    final int SQUARE_THREE_POS_X = 725;
    final int STROKE_WIDTH = 3;
    final String HELP_MESSAGE_NYC_2 = "The decay of the an isotope is determine by the formula given."
                                      + " It requires the isotopes half life and the a specific time in years."
                                      + " To begin press start, and to see your chosen isotopes decay from 1000 - 10 000 years, press the reset button.";
    final String HALF_LIFE_STRING = "The half life of";
    final String NIOBIUM_ISOTOPE = "Niobium-94";
    final String CARBON_ISOTOPE = "Carbon-14";
    final String THORIUM_ISOTOPE = "Thorium-229";
    final String MOLYBDENUM_ISOTOPE = "Molybdenum-93";
    final String CURIUM_ISOTOPE = "Curium-245";
    final String YEARS_UNIT = "Years";
    final String INPUT_DESCRIPTION = "Input years here";
    final String DEFAULT_USER_INPUT = "DEFAULT YEARS OF PROJECTION:";
    final String USER_YEARS = "User chosen project is:";
    final String G_UNITS = "grams";
    final int DEFAULT_YEARS = 6000;
    final int INITIAL_SAMPLE = 100;
    final double E_CONSTANT = 2.71828;
    final int NIOBIUM_HALF_LIFE = 20300;
    final int CARBON_HALF_LIFE = 5730;
    final int THORIUM_HALF_LIFE = 7340;
    final int MOLYBDENUM_HALF_LIFE = 4000;
    final int CURIUM_HALF_LIFE = 8500;
    final String ERROR_MESSAGE = "Please input a value from 0-6000 years.";
    final String LABEL_FINAL_YEARS_INFO = "Please pick a year to see its decay: ";
    final String INFO_LABEL = "Each gram is equivalent to approximately 1 particles.";
    final int HALF_LIFE_GRAMS = 50;
    
    //add this

    final String HELP_MESSAGE = "This animation is about Ohm's Law.\nChoose values for resistance and voltage " +
                                "and then click start to view the animation.\nClick pause to pause the animation at any time " +
                                "during the animation and click start to resume it.\nClick reset to be able to select " +
                                "new values for resistance and voltage and do the same process again.\n" +
                                "Click done to return to the main menu";
    final String RESISTANCE_1 = "10";
    final String RESISTANCE_2 = "20";
    final String RESISTANCE_3 = "50";
    final String VOLTAGE_1 = "4";
    final String VOLTAGE_2 = "6";
    final String VOLTAGE_3 = "10";
    final int SPACE_BETWEEN_RADIO_BUTTONS = 10; 
    final String CHOOSE_RESISTANCE = "Please choose a resistance";
    final String CHOOSE_VOLTAGE = "Please choose a voltage";
    final int NUMBER_OF_ELECTRONS = 73;
    final int RADIUS = 6;
    final int WIDTH_POSITION = -22;
    final int HEIGHT_POSITION = 0;
    final int WIDTH = 985;
    final int HEIGHT = 200;
    final int ELECTRONS_ANIM_DURATION = 5500;
    final int ANIMATION_DELAY = 75;
    final String YOUR_CHOICES = "Intensity from your choices: ";
    final String CURRENT_IN_AMPS = "CURRENT IN AMPERES";
    final String VOLTAGE = "VOLTAGE";
    final String CURRENT = "CURRENT";
    
    
    // 2nd anim
    double speed = 95;
    final String NYA_2_HELP_MESSAGE = "This is an animation about projectile motion\n"
            + "Click start to play the animation.\nClick reset to change the angle of the animation"
            + "and click start again to view the animation with the new angle.";
    final String INFORMATION_STRING = "INFO";
    final String CHANGING_ANGLES = "Changing angles"; 
    final String CHANGE_ANGLE = "Change the angle of your choice: ";
    final String CHANGE_ANGLE_QUESTION = "Would you like to change the angle?";
    final String ANGLE_RANGE = "Choose your angle from 5-90 here: ";
    final String DEGREES = " Degrees";
    final String X_TIME = "Time (s)";
    final String Y_HEIGHT = "Height(m)";
    final String ZERO = "0";
}
