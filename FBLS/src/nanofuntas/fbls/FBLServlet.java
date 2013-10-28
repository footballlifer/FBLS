package nanofuntas.fbls;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Servlet implementation class FBLServlet
 */
@WebServlet("/FBLServlet")
public class FBLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final boolean DEBUG = true;
	private final String TAG = "FBLServlet";
	
    /**
     * Default constructor. 
     */
    public FBLServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		out.write("1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(DEBUG) System.out.println(TAG + ": doPost()");						
		
		//TODO: image test
		if (request.getHeader("Content-type").equals("image/png")) {
			System.out.println("Content-type:image/png");
			
			String absoluteStuffPath = getServletContext().getRealPath("/");
			System.out.println("kakpple path:"+absoluteStuffPath);
			
			String path = absoluteStuffPath + "cr" + ".png";
			BufferedImage bImageFromConvert = ImageIO.read(request.getInputStream());
			
			ImageIO.write(bImageFromConvert, "png", new File(path));
		} else if (request.getHeader("Content-type").equals("get/image")) {
			System.out.println("mytype-type:get/image");
			
			String absoluteStuffPath = getServletContext().getRealPath("/");
			System.out.println("kakpple path:"+absoluteStuffPath);
			
			String path = absoluteStuffPath + "cr" + ".png";
			
			FileInputStream in = new FileInputStream(path);
			OutputStream outputstream = response.getOutputStream();

		    // Copy the contents of the file to the output stream
			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				outputstream.write(buf, 0, count);
			}
			outputstream.close();
		    in.close();
			
		} 
		else {
			// get JSON request parameters handle it and finally send it
			JSONObject jsonReq = getJsonReq(request);		
			JSONObject jsonRsp = DataHandler.handleData(jsonReq);		
			sendJsonRsp(response, jsonRsp);
		}
	}

	/**
	 * Function sendStrRsp sends String data to client as response
	 * 
	 * @response this parameter is passed from HttpServletResponse response in function doPost()
	 * @strToSend String data to be sent to client
	 */
	private void sendStrRsp(HttpServletResponse response, String strToSend) {
		if(DEBUG) System.out.println(TAG + ": sendStrRsp()");						
		
		OutputStream outStrm = null;
		ObjectOutputStream objOutStrm = null;
		
		// write date to be sent to client
		try{
			outStrm = response.getOutputStream();
			objOutStrm = new ObjectOutputStream(outStrm);
			objOutStrm.writeObject(strToSend);
			objOutStrm.flush();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			// release resources
			try{
				if(outStrm != null) outStrm.close();
				if(objOutStrm != null) objOutStrm.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * Function getStrReq extracts String data from request(HttpServletRequest request) parameter in doPost function, and return it.
	 * 
	 * @request this parameter is passed from HttpServletRequest request in function doPost()
	 * @return return String extracted from client request
	 */
	private String getStrReq(HttpServletRequest request) {
		if(DEBUG) System.out.println(TAG + ": getStrReq()");						
		
		String strReq = null;		
		InputStream inStrm = null;
		ObjectInputStream objInStrm = null;
		
		// get date from client
		try {
			inStrm = request.getInputStream();
			objInStrm = new ObjectInputStream(inStrm);
			strReq = (String) objInStrm.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){			
			e.printStackTrace();
		} finally {
			// release resources
			try{
				if(inStrm != null) inStrm.close();
				if(objInStrm != null) objInStrm.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}

		return strReq;
	}

	/**
	 * Function sendJsonRsp sends JSONObject data to client and wraps function sendStrRsp.
	 * 
	 * @response this parameter is passed from HttpServletResponse response in function doPost()
	 * @jsonToSend JSONObject data to be sent to client
	 */
	private void sendJsonRsp(HttpServletResponse response, JSONObject jsonToSend) {
		if(DEBUG) System.out.println(TAG + ": sendJsonRsp()");						
		
		String jsonStr = null;
		jsonStr = jsonToSend.toString();
		sendStrRsp(response, jsonStr);
	}	
	
	/**
	 * Function getJsonReq extracts JSONObject data from request(HttpServletRequest request) parameter in doPost function, and return it.
	 * Function getJsonReq wraps function getStrReq.
	 * 
	 * @request this parameter is passed from HttpServletRequest request in function doPost()
	 * @return return JSONObject extracted from client request
	 */
	private JSONObject getJsonReq(HttpServletRequest request){
		if(DEBUG) System.out.println(TAG + ": getJsonReq()");						
		
		String strReq = null;
		JSONObject jsonReq = null;
		strReq = getStrReq(request);
		jsonReq = (JSONObject) JSONValue.parse(strReq);
		
		return jsonReq;
	}
}
