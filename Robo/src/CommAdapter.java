
import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Junaid
 */
class CommAdapter {
   
    //Declare special symbol used in Serial Data Stream from Arduino
	private final static String start_char = "02";
	private final static String end_char = "03";
	private final static String sep_char = " ";    //space
	private final static byte zeroSignal = 0x00;   //signal to wake up module	
	
	//constant parameters for Serial communication  
	public final static int COMM_BAUDRATE_2400   = 2400;
	public final static int COMM_BAUDRATE_4800   = 4800;
	public final static int COMM_BAUDRATE_9600   = 9600;
	public final static int COMM_BAUDRATE_19200  = 19200;
	public final static int COMM_BAUDRATE_38400  = 9600;
	public final static int COMM_BAUDRATE_57600  = 9600;
	public final static int COMM_BAUDRATE_115200 = 115200;
	public final static int COMM_BAUDRATE_230400 = 230400;
	
	public final static int COMM_DATA_BITS_5 = 5;
	public final static int COMM_DATA_BITS_6 = 6;
	public final static int COMM_DATA_BITS_7 = 7;
	public final static int COMM_DATA_BITS_8 = 8;
	
	public final static int COMM_STOP_BIT_0  = 0;
	public final static int COMM_STOP_BIT_1  = 1;
	
	public final static int COMM_NONE_PARITY = 0;
	public final static int COMM_PARITY      = 1;
	
	private String[] portlist = null;
	private boolean portstatus = false;
	
	byte[] readbuffer = null; 
	byte[] writebuffer = new byte[] {0x02, 0x4d, 0x01, 0x01, 0x4f, 0x03};
	byte[] writebuffer2 = new byte[] {0x02, 0x45 };
	String dataStream = null;
	String[] payload = null;
	
	public SerialPort serialPort = null;
	
	//Constructor
	public CommAdapter() {
		
                //This constructor does not do anything
		this.serialPort = new SerialPort(null);
	}
	public void OpenComm(String commPort, int baudrate, int dataBits, int stopBits, int parity) {
		
                //Define Serial port # -- can be found in device manager
		this.serialPort = new SerialPort(commPort);		
		try {			
			//open serial port
			this.serialPort.openPort();
			//Define parameters -- baudrate, dataBits, stopBits, parity
			this.serialPort.setParams(baudrate, dataBits, stopBits, parity);
			this.portstatus = true;
                       
		} catch(SerialPortException e){
                    
                    //generate error pop up message
                    JOptionPane.showMessageDialog(null,"Port Busy Or Offline","Error",JOptionPane.INFORMATION_MESSAGE);			
	    }
	}
	
	//Get Portlists
	public String[] getPortLists() {
		portlist = SerialPortList.getPortNames();
		return portlist;
	}
	
	//Get port status
	public boolean getPortStatus() {
		return this.portstatus;
	}
	
	//Close the seleted port
	public void closePort() {
		try {
			this.serialPort.closePort();
		} catch (SerialPortException e) {
                
                //generate error pop up message
                JOptionPane.showMessageDialog(null,"No port opened to close or Port is busy","Error",JOptionPane.INFORMATION_MESSAGE);			

		}
	}
        
        //To send signal for forward direction
        public void up(){
            try {
                serialPort.writeString("1");
            } catch (SerialPortException ex) {
            
            }
        }
        
        //To send signal for downward direction
        public void down(){
            try {
                serialPort.writeString("2");
            } catch (SerialPortException ex) {
            
            }
        }
        
        //To send signal for left direction
        public void left(){
            try {
                serialPort.writeString("3");
                }
            catch (SerialPortException ex) {
            
            }
        }
        
        //To send signal for right direction
        public void right(){
            try {
                serialPort.writeString("4");
                //System.out.println(this.serialPort.readString());
            } catch (SerialPortException ex) {
            
            }
        }
        
        //To send signal for stop
        public void Stop(){
            try {
                serialPort.writeString("5");
                //System.out.println(this.serialPort.readString());
            } catch (SerialPortException ex) {
            
            }
        }        
}
